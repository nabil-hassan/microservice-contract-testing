package net.nh.domain;

import java.util.List;

public class Organisation {

    private Long id;

    private String name;

    private String countryCode;

    private Organisation publisher;

    private List<OrganisationRole> roles;

    public Organisation() {
    }

    public Organisation(String name, String countryCode, Organisation publisher, List<OrganisationRole> roles) {
        this.name = name;
        this.countryCode = countryCode;
        this.publisher = publisher;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public List<OrganisationRole> getRoles() {
        return roles;
    }

    public void setRoles(List<OrganisationRole> roles) {
        this.roles = roles;
    }

    public Organisation getPublisher() {
        return publisher;
    }

    public void setPublisher(Organisation publisher) {
        this.publisher = publisher;
    }
}
