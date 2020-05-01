package net.nh;

import net.nh.domain.Account;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.EntityNotFoundException;
import net.nh.service.AccountService;
import net.nh.service.AccountSoapTranslationService;
import net.nh.service.OrganisationService;
import net.nh.service.OrganisationSoapTranslationService;
import nh.net.api_soap_server.AccountDetail;
import nh.net.api_soap_server.AccountListResponse;
import nh.net.api_soap_server.AccountResponse;
import nh.net.api_soap_server.CreateOrUpdateOrganisationRequest;
import nh.net.api_soap_server.FindAccountByIDRequest;
import nh.net.api_soap_server.FindAccountsRequest;
import nh.net.api_soap_server.FindOrganisationByIDRequest;
import nh.net.api_soap_server.FindOrganisationsRequest;
import nh.net.api_soap_server.OrganisationDetail;
import nh.net.api_soap_server.OrganisationListResponse;
import nh.net.api_soap_server.OrganisationResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FindOrganisationByIDRequest")
    @ResponsePayload
    public OrganisationResponse findOrganisationById(@RequestPayload FindOrganisationByIDRequest request) throws EntityNotFoundException {
        Organisation organisationById = organisationService.findOrganisationById(request.getId());
        OrganisationDetail detail = organisationTranslationService.toDetail(organisationById);
        OrganisationResponse response = new OrganisationResponse();
        response.setOrganisationDetail(detail);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FindOrganisationsRequest")
    @ResponsePayload
    public OrganisationListResponse findOrganisations(@RequestPayload FindOrganisationsRequest request) {
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

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateOrUpdateOrganisationRequest")
    @ResponsePayload
    public OrganisationResponse createOrUpdateOrganisation(@RequestPayload CreateOrUpdateOrganisationRequest request) throws EntityNotFoundException {
        Organisation organisation = organisationTranslationService.toOrganisation(request);
        Organisation result;
        if (organisation.getId() != null) {
            result = organisationService.update(organisation.getId(), organisation);
        } else {
            result = organisationService.create(organisation);
        }
        OrganisationDetail detail = organisationTranslationService.toDetail(result);
        OrganisationResponse response = new OrganisationResponse();
        response.setOrganisationDetail(detail);
        return response;
    }

    // ============================================= Accounts ==========================================================
    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FindAccountByIDRequest")
    @ResponsePayload
    public AccountResponse findAccountById(@RequestPayload FindAccountByIDRequest request) throws EntityNotFoundException {
        Account accountById = accountService.findById(request.getId());
        AccountDetail accountDetail = accountTranslationService.toDetail(accountById);
        AccountResponse response = new AccountResponse();
        response.setAccountDetail(accountDetail);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FindAccountsRequest")
    @ResponsePayload
    public AccountListResponse findAccounts(@RequestPayload FindAccountsRequest request) {
        AccountListResponse response = new AccountListResponse();
        List<AccountDetail> details;
        if (request.getAdvertiserId() != null) {
            //TODO: implement
            details = Collections.emptyList();
        } else if (request.getBuyerId() != null) {
            //TODO: implement
            details = Collections.emptyList();
        } else if (request.getPublisherId() != null) {
            //TODO: implement
            details = Collections.emptyList();
        } else {
            details = accountService.findAll().stream().map(accountTranslationService::toDetail).collect(Collectors.toList());
        }
        response.getAccountDetails().addAll(details);
        return response;
    }

//    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "FindAccountByIdRequest")


//    @GetMapping(path = "/accounts/{id}", produces = APPLICATION_JSON_VALUE)
//    public ResponseEntity<AccountResponse> getAccount(@PathVariable(name = "id") Long id) {
//        try {
//            Account account = accountService.findById(id);
//            AccountResponse response = accountTranslationService.toResponse(account);
//            return ResponseEntity.ok(response);
//        } catch (EntityNotFoundException ex) {
//            return ResponseEntity.notFound().build();
//        }
//    }

}
