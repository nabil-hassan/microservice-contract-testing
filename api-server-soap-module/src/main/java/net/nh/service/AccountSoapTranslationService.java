package net.nh.service;

import net.nh.domain.Account;
import net.nh.domain.Organisation;
import nh.net.api_soap_server.AccountDetail;
import nh.net.api_soap_server.OrganisationSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AccountSoapTranslationService {

    private OrganisationSoapTranslationService organisationSoapTranslationService;

    @Autowired
    public AccountSoapTranslationService(OrganisationSoapTranslationService organisationSoapTranslationService) {
        this.organisationSoapTranslationService = organisationSoapTranslationService;
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

}
