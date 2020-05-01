package net.nh.service;

import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.OrganisationRepository;
import nh.net.api_soap_server.CreateOrUpdateOrganisationRequest;
import nh.net.api_soap_server.OrganisationDetail;
import nh.net.api_soap_server.OrganisationRoles;
import nh.net.api_soap_server.OrganisationSoapRole;
import nh.net.api_soap_server.OrganisationSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

    public OrganisationDetail toDetail(Organisation organisation) {
        OrganisationDetail detail = new OrganisationDetail();
        detail.setId(organisation.getId());
        detail.setName(organisation.getName());
        detail.setCountryCode(organisation.getCountryCode());
        detail.setPublisher(toSummary(organisation.getPublisher()));

        List<OrganisationSoapRole> soapRoles = organisation.getRoles().stream()
                .map(role -> toOrganisationSOAPRole(role)).collect(Collectors.toList());

        OrganisationRoles roles  = new OrganisationRoles();
        roles.getRoles().addAll(soapRoles);
        detail.setRoles(roles);
        return detail;
    }

    public OrganisationSoapRole toOrganisationSOAPRole(OrganisationRole role) {
        return OrganisationSoapRole.valueOf(role.name());
    }

    public OrganisationRole toOrganisationRole(OrganisationSoapRole role) {
        return OrganisationRole.valueOf(role.name());
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


    public Organisation toOrganisation(CreateOrUpdateOrganisationRequest request) {
        Organisation org = new Organisation();
        org.setId(request.getId());
        org.setCountryCode(request.getCountryCode());
        org.setName(request.getName());

        List<OrganisationRole> roles =  request.getRoles().getRoles().stream().map(this::toOrganisationRole).collect(Collectors.toList());
        org.setRoles(roles);

        Organisation publisher = organisationRepository.findOrganisationById(request.getPublisherId());
        if (publisher == null) {
            throw new IllegalArgumentException("Publisher does not exist");
        }
        org.setPublisher(publisher);
        return org;
    }
}
