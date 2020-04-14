package net.nh.service;

import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.OrganisationRepository;
import nh.net.api_soap_server.OrganisationResponse;
import nh.net.api_soap_server.OrganisationSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class OrganisationSoapTranslationService {

    private OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationSoapTranslationService(OrganisationRepository organisationRepository) {
        this.organisationRepository = organisationRepository;
    }

//    public Organisation toOrganisation(GetOrganisationRequest request) {
//        Long publisherId = request.getPublisherId();
//        Objects.requireNonNull(publisherId, "publisherID is mandatory");
//        Organisation publisher = organisationRepository.findOrganisationById(publisherId);
//        if (!publisher.getRoles().contains(OrganisationRole.PUBLISHER)) {
//            throw new IllegalArgumentException("Specified publisher organisation is not a valid publisher");
//        }
//        return new Organisation(request.getName(), request.getCountryCode(), publisher, request.getRoles());
//    }

    public OrganisationResponse toResponse(Organisation organisation) {
        OrganisationResponse response = new OrganisationResponse();
        response.setId(organisation.getId());
        response.setName(organisation.getName());
        response.setCountryCode(organisation.getCountryCode());
        response.setPublisher(toSummary(organisation.getPublisher()));

        //TODO: implement
//        List<OrganisationRole> roles = organisation.getRoles();
        return response;
    }

    public OrganisationSummary toSummary(Organisation organisation) {
        if (organisation == null) {
            return null;
        }
        OrganisationSummary organisationSummary = new OrganisationSummary();
        organisationSummary.setId(organisation.getId());
        organisationSummary.setName(organisation.getName());
        return organisationSummary;
    }



}
