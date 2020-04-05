package net.nh.api.rest;

import net.nh.domain.Account;
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
    public ResponseEntity<?> createAccount(@PathVariable(name = "id") Long id, @RequestBody AccountRequest account) {
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


}
