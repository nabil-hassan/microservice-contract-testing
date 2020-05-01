package net.nh.api.rest.entities;

import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;

import java.util.List;

public class OrganisationRequest {

    private String name;

    private String countryCode;

    private Long publisherId;

    private List<OrganisationRole> roles;

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

    public Long getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Long publisherId) {
        this.publisherId = publisherId;
    }

    public List<OrganisationRole> getRoles() {
        return roles;
    }

    public void setRoles(List<OrganisationRole> roles) {
        this.roles = roles;
    }
}
