package net.nh.service;

import net.nh.domain.Account;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.OrganisationRepository;
import nh.net.api_soap_server.AccountDetail;
import nh.net.api_soap_server.CreateOrUpdateAccountRequest;
import nh.net.api_soap_server.OrganisationSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AccountSoapTranslationService {

    private OrganisationSoapTranslationService organisationSoapTranslationService;
    private OrganisationRepository organisationRepository;

    @Autowired
    public AccountSoapTranslationService(OrganisationSoapTranslationService organisationSoapTranslationService,
                                         OrganisationRepository organisationRepository) {
        this.organisationSoapTranslationService = organisationSoapTranslationService;
        this.organisationRepository = organisationRepository;
    }

    public AccountDetail toDetail(Account account) {
        AccountDetail detail = new AccountDetail();
        detail.setId(account.getId());
        detail.setExternalId(account.getExternalId());
        detail.setAdvertiser(organisationSoapTranslationService.toSummary(account.getAdvertiser()));
        detail.setBuyer(organisationSoapTranslationService.toSummary(account.getBuyer()));
        detail.setPublisher(organisationSoapTranslationService.toSummary(account.getPublisher()));
        return detail;
    }

    public Account toAccount(CreateOrUpdateAccountRequest request) {
        Account account = new Account();
        account.setId(request.getId());
        account.setExternalId(request.getExternalId());

        Organisation advertiser = organisationRepository.findOrganisationById(request.getAdvertiserId());
        account.setAdvertiser(advertiser);

        Organisation buyer = organisationRepository.findOrganisationById(request.getBuyerId());
        account.setBuyer(buyer);

        Organisation publisher = organisationRepository.findOrganisationById(request.getPublisherId());
        account.setPublisher(publisher);
        return account;
    }
}
