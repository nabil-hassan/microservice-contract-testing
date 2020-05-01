package net.nh.api.rest.entities;

import com.fasterxml.jackson.annotation.JsonInclude;

public class AccountResponse {

    private Long id;
    private String externalId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrganisationSummary buyer;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrganisationSummary advertiser;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrganisationSummary publisher;

    public AccountResponse() {
    }

    public AccountResponse(Long id, String externalId, OrganisationSummary buyer,
                           OrganisationSummary advertiser, OrganisationSummary publisher) {
        this.id = id;
        this.externalId = externalId;
        this.buyer = buyer;
        this.advertiser = advertiser;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public String getExternalId() {
        return externalId;
    }

    public OrganisationSummary getBuyer() {
        return buyer;
    }

    public OrganisationSummary getAdvertiser() {
        return advertiser;
    }

    public OrganisationSummary getPublisher() {
        return publisher;
    }
}
