package net.nh.api.rest;

import net.nh.domain.Account;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.AccountRepository;
import net.nh.repository.EntityNotFoundException;
import net.nh.repository.OrganisationRepository;
import net.nh.service.AccountService;
import net.nh.service.OrganisationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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
import java.util.Map;

import static org.springframework.http.MediaType.*;

@RestController
@RequestMapping("/rest")
public class ApplicationRestController {

    private AccountService accountService;
    private OrganisationService organisationService;

    @Autowired
    public ApplicationRestController(AccountService accountService, OrganisationService organisationService) {
        this.accountService = accountService;
        this.organisationService = organisationService;
    }

    // =================================================== Accounts ====================================================
    @PostMapping(path = "/accounts", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAccount(@RequestBody AccountRequest account) {
        try {
            return ResponseEntity.ok(accountService.createAccount(account));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(path = "/accounts/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateAccount(@PathVariable(name = "id") Long id, @RequestBody AccountRequest account) {
        try {
            return ResponseEntity.ok(accountService.updateAccount(id, account));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/accounts/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Account> getAccount(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(accountService.findById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/accounts", produces = APPLICATION_JSON_VALUE)
    public List<Account> getAccounts(@RequestParam(name = "publisherId", required = false) Long publisherId) {
        if (publisherId == null) {
            return accountService.findAll();
        } else {
            return accountService.findByPublisher(publisherId);
        }
    }

    // =================================================== Organisations ===============================================
    @PostMapping(path = "/organisations", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrganisation(@RequestBody OrganisationRequest request) {
        try {
            return ResponseEntity.ok(organisationService.create(request));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping(path = "/organisations/{id}", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOrganisation(@PathVariable(name = "id") Long id, @RequestBody OrganisationRequest request) {
        try {
            return ResponseEntity.ok(organisationService.update(id, request));
        } catch (Exception ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @GetMapping(path = "/organisations/{id}", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<Organisation> getOrganisation(@PathVariable(name = "id") Long id) {
        try {
            return ResponseEntity.ok(organisationService.findOrganisationById(id));
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/organisations", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getOrganisations(@RequestParam(name = "publisherId", required = false) Long publisherId,
                                          @RequestParam(name = "countryCode", required = false) String countryCode,
                                          @RequestParam(name = "role", required = false) String role) {
        if (publisherId == null && countryCode == null && role == null) {
            return ResponseEntity.ok(organisationService.findOrganisations());
        }
        if (publisherId != null) {
            return ResponseEntity.ok(organisationService.findOrganisationsByPublisher(publisherId));
        }
        if (role != null) {
            OrganisationRole orgRole;
            try {
                orgRole = OrganisationRole.valueOf(role.toUpperCase());
            } catch (Exception e) {
                return ResponseEntity.badRequest().body("Invalid role");
            }
            if (countryCode == null) {
                return ResponseEntity.ok(organisationService.findOrganisationsByRole(orgRole));
            } else {
                return ResponseEntity.ok(organisationService.findOrganisationsByCountryCodeAndRole(countryCode, orgRole));
            }
        }
        return ResponseEntity.badRequest().body("Invalid query parameter combination");
    }

}
