package net.nh.api.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.nh.domain.OrganisationRole;

import java.util.List;

public class OrganisationResponse {

    private Long id;
    private String name;
    private String countryCode;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private OrganisationSummary publisher;
    private List<OrganisationRole> roles;

    public OrganisationResponse() {
    }

    public OrganisationResponse(Long id, String name, String countryCode,
                                OrganisationSummary publisher, List<OrganisationRole> roles) {
        this.id = id;
        this.name = name;
        this.countryCode = countryCode;
        this.publisher = publisher;
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public OrganisationSummary getPublisher() {
        return publisher;
    }

    public List<OrganisationRole> getRoles() {
        return roles;
    }
}
