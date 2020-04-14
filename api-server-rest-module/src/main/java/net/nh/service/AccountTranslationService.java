package net.nh.service;

import net.nh.api.rest.AccountRequest;
import net.nh.api.rest.AccountResponse;
import net.nh.api.rest.OrganisationSummary;
import net.nh.domain.Account;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.EntityNotFoundException;
import net.nh.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class AccountTranslationService {

    private OrganisationRepository organisationRepository;
    private OrganisationTranslationService organisationTranslationService;

    @Autowired
    public AccountTranslationService(OrganisationRepository organisationRepository, OrganisationTranslationService organisationTranslationService) {
        this.organisationRepository = organisationRepository;
        this.organisationTranslationService = organisationTranslationService;
    }

    public Account toAccount(AccountRequest accountRequest) throws EntityNotFoundException {
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

    public AccountResponse toResponse(Account account) {
        Long id = account.getId();
        String externalId = account.getExternalId();
        OrganisationSummary advertiser = organisationTranslationService.toSummary(account.getAdvertiser());
        OrganisationSummary buyer = organisationTranslationService.toSummary(account.getBuyer());
        OrganisationSummary publisher = organisationTranslationService.toSummary(account.getPublisher());
        return new AccountResponse(id, externalId, buyer, advertiser, publisher);
    }

}
