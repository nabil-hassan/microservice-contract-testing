package net.nh;

import net.nh.domain.Organisation;
import net.nh.repository.EntityNotFoundException;
import net.nh.repository.OrganisationRepository;
import net.nh.service.AccountService;
import net.nh.service.AccountSoapTranslationService;
import net.nh.service.OrganisationService;
import net.nh.service.OrganisationSoapTranslationService;
import nh.net.api_soap_server.FindOrganisationByID;
import nh.net.api_soap_server.OrganisationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

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
    public OrganisationResponse findOrganisationById(@RequestPayload FindOrganisationByID request) {
        try {
            Organisation organisationById = organisationService.findOrganisationById(request.getId());
            OrganisationResponse response = organisationTranslationService.toResponse(organisationById);
            return response;
        } catch (EntityNotFoundException ex) {
            //TODO: proper handling???
            throw new RuntimeException("Specified organisation does not exist", ex);
        }
    }

}
