package net.nh.api.rest;

import net.nh.domain.Account;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.EntityNotFoundException;
import net.nh.service.AccountService;
import net.nh.service.OrganisationService;
import net.nh.service.AccountRestTranslationService;
import net.nh.service.OrganisationRestTranslationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/rest")
public class ApplicationRestController {

    private AccountService accountService;
    private OrganisationService organisationService;
    private AccountRestTranslationService accountTranslationService;
    private OrganisationRestTranslationService organisationTranslationService;

    @Autowired
    public ApplicationRestController(AccountService accountService, OrganisationService organisationService,
                                     AccountRestTranslationService accountTranslationService, OrganisationRestTranslationService organisationTranslationService) {
        this.accountService = accountService;
        this.organisationService = organisationService;
        this.accountTranslationService = accountTranslationService;
        this.organisationTranslationService = organisationTranslationService;
    }

    // =================================================== Accounts ====================================================
    @PostMapping(path = "/accounts", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccount(@RequestBody AccountRequest request) {
        try {
            Account account = accountTranslationService.toAccount(request);
            Account created = accountService.createAccount(account);
            AccountResponse response = accountTranslationService.toResponse(created);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(path = "/accounts/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAccount(@PathVariable(name = "id") Long id, @RequestBody AccountRequest request) {
        try {
            Account account = accountTranslationService.toAccount(request);
            Account updated = accountService.updateAccount(id, account);
            AccountResponse response = accountTranslationService.toResponse(updated);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/accounts/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<AccountResponse> getAccount(@PathVariable(name = "id") Long id) {
        try {
            Account account = accountService.findById(id);
            AccountResponse response = accountTranslationService.toResponse(account);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/accounts", produces = APPLICATION_JSON_VALUE)
    public List<AccountResponse> getAccounts(@RequestParam(name = "publisherId", required = false) Long publisherId) {
        List<Account> accounts;
        if (publisherId == null) {
            accounts = accountService.findAll();
        } else {
            accounts = accountService.findByPublisher(publisherId);
        }
        return accounts.stream()
                .map(accountTranslationService::toResponse).collect(Collectors.toList());
    }

    // =================================================== Organisations ===============================================
    @PostMapping(path = "/organisations", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrganisation(@RequestBody OrganisationRequest request) {
        try {
            Organisation organisation = organisationTranslationService.toOrganisation(request);
            Organisation created = organisationService.create(organisation);
            OrganisationResponse response = organisationTranslationService.toResponse(created);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(path = "/organisations/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOrganisation(@PathVariable(name = "id") Long id, @RequestBody OrganisationRequest request) {
        try {
            Organisation organisation = organisationTranslationService.toOrganisation(request);
            Organisation updated = organisationService.update(id, organisation);
            OrganisationResponse response = organisationTranslationService.toResponse(updated);
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/organisations/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<OrganisationResponse> getOrganisation(@PathVariable(name = "id") Long id) {
        try {
            Organisation organisationById = organisationService.findOrganisationById(id);
            OrganisationResponse response = organisationTranslationService.toResponse(organisationById);
            return ResponseEntity.ok(response);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/organisations", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrganisations(@RequestParam(name = "publisherId", required = false) Long publisherId,
                                          @RequestParam(name = "countryCode", required = false) String countryCode,
                                          @RequestParam(name = "role", required = false) String role) {
        List<Organisation> organisations = null;
        if (publisherId == null && countryCode == null && role == null) {
            organisations = organisationService.findOrganisations();
        }
        if (publisherId != null) {
            organisations = organisationService.findOrganisationsByPublisher(publisherId);
        }
        if (countryCode != null && role == null) {
            organisations = organisationService.findOrganisationsByCountryCode(countryCode);
        }
        if (role != null) {
            OrganisationRole orgRole;
            try {
                orgRole = OrganisationRole.valueOf(role.toUpperCase());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Invalid role");
            }
            if (countryCode == null) {
                organisations = organisationService.findOrganisationsByRole(orgRole);
            } else {
                organisations = organisationService.findOrganisationsByCountryCodeAndRole(countryCode, orgRole);
            }
        }
        List<OrganisationResponse> response = organisations.stream()
                .map(organisationTranslationService::toResponse).collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }

}
