package net.nh;

import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.EntityNotFoundException;
import net.nh.service.AccountService;
import net.nh.service.AccountSoapTranslationService;
import net.nh.service.OrganisationService;
import net.nh.service.OrganisationSoapTranslationService;
import nh.net.api_soap_server.FindOrganisationByID;
import nh.net.api_soap_server.FindOrganisations;
import nh.net.api_soap_server.OrganisationDetail;
import nh.net.api_soap_server.OrganisationListResponse;
import nh.net.api_soap_server.OrganisationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Endpoint
public class ApplicationEndpoint {

    public static final String NAMESPACE_URI = "http://net.nh/api-soap-server";

    private AccountService accountService;
    private AccountSoapTranslationService accountTranslationService;
    private OrganisationService organisationService;
    private OrganisationSoapTranslationService organisationTranslationService;

    @Autowired
    public ApplicationEndpoint(AccountService accountService, AccountSoapTranslationService accountTranslationService,
                               OrganisationService organisationService, OrganisationSoapTranslationService organisationTranslationService) {
        this.accountService = accountService;
        this.accountTranslationService = accountTranslationService;
        this.organisationService = organisationService;
        this.organisationTranslationService = organisationTranslationService;
    }

    // ======================================== Organisations ==========================================================
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findOrganisationByID")
    @ResponsePayload
    public OrganisationResponse findOrganisationById(@RequestPayload FindOrganisationByID request) throws EntityNotFoundException {
        Organisation organisationById = organisationService.findOrganisationById(request.getId());
        OrganisationDetail detail = organisationTranslationService.toDetail(organisationById);
        OrganisationResponse response = new OrganisationResponse();
        response.setOrganisationDetail(detail);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "findOrganisations")
    @ResponsePayload
    public OrganisationListResponse findOrganisations(@RequestPayload FindOrganisations request) {
        OrganisationListResponse response = new OrganisationListResponse();
        OrganisationRole orgRole = null;
        if (request.getRole() != null) {
            try {
                orgRole = organisationTranslationService.toOrganisationRole(request.getRole());
            } catch (Exception e) {
                throw new IllegalArgumentException("Invalid role: " + request.getRole());
            }
        }
        List<Organisation> organisations = organisationService.findOrganisations(request.getPublisherId(), request.getCountryCode(), orgRole);
        List<OrganisationDetail> results = organisations.stream()
                .map(organisationTranslationService::toDetail).collect(toList());
        response.getOrgDetails().addAll(results);
        return response;
    }

    // TODO: Create organisation

    // TODO: Update organisation

}
