package net.nh.repository;

import net.nh.domain.Account;

import java.util.List;

public interface AccountRepository {

    Account findById(Long id) throws EntityNotFoundException;

    List<Account> findAll();

    List<Account> findByPublisher(Long publisherId);

    Account create(Account account);

    Account update(Account account) throws EntityNotFoundException;

}
