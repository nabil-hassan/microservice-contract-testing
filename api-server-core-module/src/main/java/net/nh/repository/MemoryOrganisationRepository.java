package net.nh.repository;

import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class MemoryOrganisationRepository implements OrganisationRepository {

    private long idSequenceNumber;
    private Map<Long, Organisation> datasource;

    public MemoryOrganisationRepository() {
        datasource = new HashMap<>();
    }

    @Override
    public Organisation findOrganisationById(Long id) {
        return datasource.get(id);
    }

    public List<Organisation> findOrganisations(Long publisherId, String countryCode, OrganisationRole role) {
        if (publisherId == null && countryCode == null && role == null) {
            return datasource.values().stream()
                    .sorted(Comparator.comparing(Organisation::getId)).collect(Collectors.toList());
        } else {
            Predicate<Organisation> predicate = buildOrganisationSearchPredicate(publisherId, countryCode, role);
            return datasource.values().stream()
                    .filter(predicate)
                    .sorted(Comparator.comparing(Organisation::getId)).collect(Collectors.toList());
        }
    }

    @Override
    public Organisation create(Organisation organisation) {
        synchronized (this) {
            organisation.setId(++idSequenceNumber);
            datasource.put(organisation.getId(), organisation);
        }
        return organisation;
    }

    @Override
    public Organisation update(Organisation organisation) {
        synchronized (this) {
            datasource.put(organisation.getId(), organisation);
        }
        return organisation;
    }

    private Predicate<Organisation> buildOrganisationSearchPredicate(Long publisherId, String countryCode,
                                                                     OrganisationRole role) {
        List<Predicate<Organisation>> predicates = new ArrayList<>(3);
        if (publisherId != null)
            predicates.add(org -> org.getPublisher() != null && org.getPublisher().getId().equals(publisherId));
        if (countryCode != null)
            predicates.add(org -> org.getCountryCode().equals(countryCode));
        if (role != null)
            predicates.add(org -> org.getRoles().contains(role));

        Predicate<Organisation> result = predicates.get(0);
        for (int i = 1; i < predicates.size(); i++) {
            result = result.and(predicates.get(i));
        }
        return result;
    }

}
