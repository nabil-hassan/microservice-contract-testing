package net.nh.repository;

import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;

import java.util.List;

public interface OrganisationRepository {

    Organisation findOrganisationById(Long id) throws EntityNotFoundException;

    List<Organisation> findOrganisations();

    List<Organisation> findOrganisationsByPublisher(Long publisherId);

    List<Organisation> findOrganisationsByCountryCode(String countryCode);

    List<Organisation> findOrganisationsByRole(OrganisationRole role);

    List<Organisation> findOrganisationsByCountryCodeAndRole(String countryCode, OrganisationRole role);

    Organisation create(Organisation organisation);

    Organisation update(Long id, Organisation organisation) throws EntityNotFoundException;

}
