package net.nh.repository;

import net.nh.domain.Account;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Repository
public class MemoryAccountRepository implements AccountRepository {

    private long idSequenceNumber;
    private Map<Long, Account> dataSource;

    public MemoryAccountRepository() {
        dataSource = new HashMap<>();
    }

    @Override
    public Account findById(Long id) throws EntityNotFoundException {
        return dataSource.get(id);
    }

    @Override
    public List<Account> findAll() {
        return dataSource.values().stream()
                .sorted(Comparator.comparing(Account::getId)).collect(Collectors.toList());
    }

    @Override
    public List<Account> findByPublisher(Long publisherId) {
        return dataSource.values().stream()
                .filter(a -> a.getPublisher().getId().equals(publisherId))
                .sorted(Comparator.comparing(Account::getId)).collect(Collectors.toList());
    }

    @Override
    public List<Account> findByAdvertiser(Long advertiserId) {
        return dataSource.values().stream()
                .filter(a -> a.getAdvertiser().getId().equals(advertiserId))
                .sorted(Comparator.comparing(Account::getId)).collect(Collectors.toList());
    }

    @Override
    public List<Account> findByBuyer(Long buyerId) {
        return dataSource.values().stream()
                .filter(a -> a.getBuyer().getId().equals(buyerId))
                .sorted(Comparator.comparing(Account::getId)).collect(Collectors.toList());
    }

    @Override
    public Account create(Account account) {
        synchronized (this) {
            account.setId(++idSequenceNumber);
            dataSource.put(account.getId(), account);
        }
        return account;
    }

    @Override
    public Account update(Account account) throws EntityNotFoundException {
        synchronized (this) {
            dataSource.put(account.getId(), account);
        }
        return account;
    }

}
