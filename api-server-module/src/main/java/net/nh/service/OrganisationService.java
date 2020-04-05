package net.nh.service;

import net.nh.api.rest.OrganisationRequest;
import net.nh.domain.Account;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.EntityNotFoundException;
import net.nh.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class OrganisationService {

    private OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public Organisation create(OrganisationRequest request) {
        Organisation organisation = buildOrganisationFromRequest(request);
        return organisationRepository.create(organisation);
    }

    public Organisation update(Long id, OrganisationRequest request) throws Exception {
        Organisation existing = organisationRepository.findOrganisationById(id);
        if (existing == null) {
            throw new EntityNotFoundException(Organisation.class, id);
        }
        Organisation organisation = buildOrganisationFromRequest(request);
        organisation.setId(id);
        return organisationRepository.update(organisation);
    } 

    public Organisation findOrganisationById(Long id) throws EntityNotFoundException {
        Organisation organisation = organisationRepository.findOrganisationById(id);
        if (organisation == null) {
            throw new EntityNotFoundException(Organisation.class, id);
        }
        return organisation;
    }

    public List<Organisation> findOrganisations() {
        return organisationRepository.findOrganisations();
    }

    public List<Organisation> findOrganisationsByPublisher(Long publisherId) {
        return organisationRepository.findOrganisationsByPublisher(publisherId);
    }

    public List<Organisation> findOrganisationsByCountryCode(String countryCode) {
        return organisationRepository.findOrganisationsByCountryCode(countryCode);
    }

    public List<Organisation> findOrganisationsByRole(OrganisationRole role) {
        return organisationRepository.findOrganisationsByRole(role);
    }

    public List<Organisation> findOrganisationsByCountryCodeAndRole(String countryCode, OrganisationRole role) {
        return organisationRepository.findOrganisationsByCountryCodeAndRole(countryCode, role);
    }

    private Organisation buildOrganisationFromRequest(OrganisationRequest request) {
        Long publisherId = request.getPublisherId();
        Objects.requireNonNull(publisherId, "publisherID is mandatory");
        Organisation publisher = organisationRepository.findOrganisationById(publisherId);
        if (!publisher.getRoles().contains(OrganisationRole.PUBLISHER)) {
            throw new IllegalArgumentException("Specified publisher organisation is not a valid publisher");
        }
        return new Organisation(request.getName(), request.getCountryCode(), publisher, request.getRoles());
    }
    
}
