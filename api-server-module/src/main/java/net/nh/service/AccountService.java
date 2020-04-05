package net.nh.service;

import net.nh.api.rest.AccountRequest;
import net.nh.domain.Account;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.AccountRepository;
import net.nh.repository.EntityNotFoundException;
import net.nh.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {

    private AccountRepository accountRepository;
    private OrganisationRepository organisationRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository, OrganisationRepository organisationRepository) {
        this.accountRepository = accountRepository;
        this.organisationRepository = organisationRepository;
    }

    public Account createAccount(AccountRequest accountRequest) throws Exception {
        Account account = buildAccountFromRequest(accountRequest);
        return accountRepository.create(account);
    }

    public Account updateAccount(Long id, AccountRequest request) throws Exception {
        Account existing = accountRepository.findById(id);
        if (existing == null) {
            throw new EntityNotFoundException(Account.class, id);
        }
        Account account = buildAccountFromRequest(request);
        account.setId(id);
        return accountRepository.update(account);
    }

    public Account findById(Long id) throws EntityNotFoundException {
        Account account = accountRepository.findById(id);
        if (account == null) {
            throw new EntityNotFoundException(Account.class, id);
        }
        return account;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public List<Account> findByPublisher(Long publisherId) {
        return accountRepository.findByPublisher(publisherId);
    }

    private Account buildAccountFromRequest(AccountRequest accountRequest) throws EntityNotFoundException {
        Long advertiserId = accountRequest.getAdvertiserId();
        Objects.requireNonNull(advertiserId, "advertiserID is mandatory");
        Organisation advertiser = organisationRepository.findOrganisationById(advertiserId);
        if (!advertiser.getRoles().contains(OrganisationRole.ADVERTISER)) {
            throw new IllegalArgumentException("Specified advertiser organisation is not a valid advertiser");
        }

        Long buyerId = accountRequest.getBuyerId();
        Objects.requireNonNull(buyerId, "buyerID is mandatory");
        Organisation buyer = organisationRepository.findOrganisationById(buyerId);
        if (!buyer.getRoles().contains(OrganisationRole.BUYER)) {
            throw new IllegalArgumentException("Specified buyer organisation is not a valid buyer");
        }

        Long publisherId = accountRequest.getPublisherId();
        Objects.requireNonNull(publisherId, "publisherID is mandatory");
        Organisation publisher = organisationRepository.findOrganisationById(publisherId);
        if (!publisher.getRoles().contains(OrganisationRole.PUBLISHER)) {
            throw new IllegalArgumentException("Specified publisher organisation is not a valid publisher");
        }

        return new Account(accountRequest.getExternalId(), buyer, advertiser, publisher);
    }
}
