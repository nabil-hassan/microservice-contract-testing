package net.nh.domain;

public class Account {

    private Long id;

    private String externalId;

    private Organisation buyer;

    private Organisation advertiser;

    private Organisation publisher;

    public Account() {
    }

    public Account(String externalId, Organisation buyer, Organisation advertiser, Organisation publisher) {
        this.externalId = externalId;
        this.buyer = buyer;
        this.advertiser = advertiser;
        this.publisher = publisher;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public Organisation getBuyer() {
        return buyer;
    }

    public void setBuyer(Organisation buyer) {
        this.buyer = buyer;
    }

    public Organisation getAdvertiser() {
        return advertiser;
    }

    public void setAdvertiser(Organisation advertiser) {
        this.advertiser = advertiser;
    }

    public Organisation getPublisher() {
        return publisher;
    }

    public void setPublisher(Organisation publisher) {
        this.publisher = publisher;
    }
}
