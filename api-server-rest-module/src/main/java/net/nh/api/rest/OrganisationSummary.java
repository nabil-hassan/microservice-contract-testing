package net.nh.api.rest;

public class OrganisationSummary {

    private Long id;
    private String name;

    public OrganisationSummary() {
    }

    public OrganisationSummary(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
