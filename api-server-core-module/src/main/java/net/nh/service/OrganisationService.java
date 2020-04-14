package net.nh.service;

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

    public Organisation create(Organisation organisation) {
        return organisationRepository.create(organisation);
    }

    public Organisation update(Long id, Organisation organisation) throws Exception {
        Organisation existing = organisationRepository.findOrganisationById(id);
        if (existing == null) {
            throw new EntityNotFoundException(Organisation.class, id);
        }
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

}
