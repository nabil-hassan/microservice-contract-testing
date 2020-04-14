package net.nh.service;

import net.nh.api.rest.OrganisationRequest;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class OrganisationTranslationService {

    private OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationTranslationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

    public Organisation buildOrganisationFromRequest(OrganisationRequest request) {
        Long publisherId = request.getPublisherId();
        Objects.requireNonNull(publisherId, "publisherID is mandatory");
        Organisation publisher = organisationRepository.findOrganisationById(publisherId);
        if (!publisher.getRoles().contains(OrganisationRole.PUBLISHER)) {
            throw new IllegalArgumentException("Specified publisher organisation is not a valid publisher");
        }
        return new Organisation(request.getName(), request.getCountryCode(), publisher, request.getRoles());
    }
}
