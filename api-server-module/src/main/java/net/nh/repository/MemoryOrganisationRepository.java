package net.nh.repository;

import net.nh.domain.Account;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class MemoryOrganisationRepository implements OrganisationRepository {

    private long idSequenceNumber;
    private Map<Long, Organisation> datasource;

    public MemoryOrganisationRepository() {
        datasource = new HashMap<>();
    }

    @Override
    public Organisation findOrganisationById(Long id) throws EntityNotFoundException {
        Organisation organisation = datasource.get(id);
        if (organisation == null) {
            throw new EntityNotFoundException(this.getClass(), id);
        }
        return organisation;
    }

    @Override
    public List<Organisation> findOrganisations() {
        return datasource.values().stream()
                .sorted(Comparator.comparing(Organisation::getId)).collect(Collectors.toList());
    }

    @Override
    public List<Organisation> findOrganisationsByPublisher(Long publisherId) {
        Objects.requireNonNull(publisherId);
        return datasource.values().stream()
                .filter(o -> o.getPublisher().getId().equals(publisherId))
                .sorted(Comparator.comparing(Organisation::getId)).collect(Collectors.toList());
    }

    @Override
    public List<Organisation> findOrganisationsByCountryCode(String countryCode) {
        Objects.requireNonNull(countryCode);
        return datasource.values().stream()
                .filter(o -> o.getCountryCode().equals(countryCode))
                .sorted(Comparator.comparing(Organisation::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Organisation> findOrganisationsByRole(OrganisationRole role) {
        Objects.requireNonNull(role);
        return datasource.values().stream()
                .filter(o -> o.getRoles().contains(role))
                .sorted(Comparator.comparing(Organisation::getId))
                .collect(Collectors.toList());
    }

    @Override
    public List<Organisation> findOrganisationsByCountryCodeAndRole(String countryCode, OrganisationRole role) {
        Objects.requireNonNull(countryCode);
        Objects.requireNonNull(role);
        return datasource.values().stream()
                .filter(o -> o.getCountryCode().equals(countryCode) && o.getRoles().contains(role))
                .sorted(Comparator.comparing(Organisation::getId))
                .collect(Collectors.toList());
    }

    @Override
    public Organisation create(Organisation organisation) {
        Objects.requireNonNull(organisation);
        synchronized (this) {
            organisation.setId(++idSequenceNumber);
            datasource.put(organisation.getId(), organisation);
        }
        return organisation;
    }

    @Override
    public Organisation update(Long id, Organisation organisation) throws EntityNotFoundException {
        Objects.requireNonNull(id);
        Objects.requireNonNull(organisation);
        if (!datasource.containsKey(id)) {
            throw new EntityNotFoundException(this.getClass(), id);
        }
        synchronized (this) {
            datasource.put(id, organisation);
        }
        return organisation;
    }

}
