package net.nh.repository;

import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;

import java.util.List;

public interface OrganisationRepository {

    Organisation findOrganisationById(Long id);

    List<Organisation> findOrganisations(Long publisherId, String countryCode, OrganisationRole role);

    Organisation create(Organisation organisation);

    Organisation update(Organisation organisation);

}
