package net.nh.repository;

import net.nh.domain.Account;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class MemoryRepositoryInitialiser implements InitializingBean {

    private static final Logger LOG = LoggerFactory.getLogger(MemoryRepositoryInitialiser.class);
    private Long sequence = 1L;

    private MemoryAccountRepository accountRepository;
    private MemoryOrganisationRepository organisationRepository;

    @Autowired
    public MemoryRepositoryInitialiser(MemoryAccountRepository accountRepository, MemoryOrganisationRepository organisationRepository) {
        this.accountRepository = accountRepository;
        this.organisationRepository = organisationRepository;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        LOG.info("Initialising memory data sources");

        // =================================== Create 2 top level publishers ===========================================
        Organisation uk_publisher = new Organisation("UK_PUBLISHER", "UK", null, List.of(OrganisationRole.PUBLISHER));
        organisationRepository.create(uk_publisher);

        Organisation eu_publisher = new Organisation("EU_PUBLISHER", "EU", null, List.of(OrganisationRole.PUBLISHER));
        organisationRepository.create(eu_publisher);

        // ================================== Create 10 advertisers & buyers per publisher =============================
        for (int i = 0; i < 10; i++) {
            Organisation advertiser = buildOrganisation(uk_publisher, OrganisationRole.ADVERTISER, "UK");
            organisationRepository.create(advertiser);

            Organisation buyer = buildOrganisation(uk_publisher, OrganisationRole.BUYER, "UK");
            organisationRepository.create(buyer);
        }

        for (int i = 0; i < 10; i++) {
            Organisation advertiser = buildOrganisation(eu_publisher, OrganisationRole.ADVERTISER, "EU");
            organisationRepository.create(advertiser);

            Organisation buyer = buildOrganisation(eu_publisher, OrganisationRole.BUYER, "EU");
            organisationRepository.create(buyer);
        }
        // ======================================== Create 10 UK accounts =============================================
        List<Organisation> ukAdvertisers = organisationRepository
                .findOrganisations(null, "UK", OrganisationRole.ADVERTISER);

        List<Organisation> ukBuyers = organisationRepository
                .findOrganisations(null, "UK", OrganisationRole.BUYER);

        for (int i = 0; i < 10; i++) {
            Organisation advertiser = ukAdvertisers.get(ThreadLocalRandom.current().nextInt(0, 10));
            Organisation buyer = ukBuyers.get(ThreadLocalRandom.current().nextInt(0, 10));
            String externalId = String.valueOf(ThreadLocalRandom.current().nextInt(0, 10000));
            Account account = new Account(externalId, buyer, advertiser, uk_publisher);
            accountRepository.create(account);
        }


        // ======================================== Create 10 EU accounts =============================================
        List<Organisation> euAdvertisers = organisationRepository
                .findOrganisations(null, "EU", OrganisationRole.ADVERTISER);

        List<Organisation> euBuyers = organisationRepository
                .findOrganisations(null, "EU", OrganisationRole.BUYER);

        for (int i = 0; i < 10; i++) {
            Organisation advertiser = euAdvertisers.get(ThreadLocalRandom.current().nextInt(0, 10));
            Organisation buyer = euBuyers.get(ThreadLocalRandom.current().nextInt(0, 10));
            String externalId = String.valueOf(ThreadLocalRandom.current().nextInt(0, 10000));
            Account account = new Account(externalId, buyer, advertiser, eu_publisher);
            accountRepository.create(account);
        }
    }

    private Organisation buildOrganisation(Organisation publisher, OrganisationRole role, String country) {
        return new Organisation(role.name() + "_" + (sequence++), country, publisher, List.of(role));
    }

}
