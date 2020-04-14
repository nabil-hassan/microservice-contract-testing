package net.nh.service;

import net.nh.api.rest.OrganisationRequest;
import net.nh.api.rest.OrganisationResponse;
import net.nh.api.rest.OrganisationSummary;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class OrganisationRestTranslationService {

    private OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationRestTranslationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public Organisation toOrganisation(OrganisationRequest request) {
        Long publisherId = request.getPublisherId();
        Objects.requireNonNull(publisherId, "publisherID is mandatory");
        Organisation publisher = organisationRepository.findOrganisationById(publisherId);
        if (!publisher.getRoles().contains(OrganisationRole.PUBLISHER)) {
            throw new IllegalArgumentException("Specified publisher organisation is not a valid publisher");
        }
        return new Organisation(request.getName(), request.getCountryCode(), publisher, request.getRoles());
    }

    public OrganisationResponse toResponse(Organisation organisation) {
        Long id = organisation.getId();
        String name = organisation.getName();
        String countryCode = organisation.getCountryCode();
        OrganisationSummary publisher = toSummary(organisation.getPublisher());
        List<OrganisationRole> roles = organisation.getRoles();
        return new OrganisationResponse(id, name, countryCode, publisher, roles);
    }

    public OrganisationSummary toSummary(Organisation organisation) {
        return organisation == null ? null : new OrganisationSummary(organisation.getId(), organisation.getName());
    }
}
