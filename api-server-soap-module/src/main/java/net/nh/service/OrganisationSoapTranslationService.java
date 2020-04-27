package net.nh.service;

import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.OrganisationRepository;
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
        OrganisationRoles organisationRoles = new OrganisationRoles();
        detail.setId(organisation.getId());
        detail.setName(organisation.getName());
        detail.setCountryCode(organisation.getCountryCode());
        detail.setPublisher(toSummary(organisation.getPublisher()));
        detail.setRoles(organisationRoles);

        List<OrganisationSoapRole> soapRoles = organisation.getRoles().stream()
                .map(role -> toOrganisationSOAPRole(role)).collect(Collectors.toList());
        organisationRoles.getRoles().addAll(soapRoles);
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



}
