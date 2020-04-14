package net.nh.service;

import net.nh.domain.Account;
import net.nh.domain.Organisation;
import net.nh.domain.OrganisationRole;
import net.nh.repository.AccountRepository;
import net.nh.repository.EntityNotFoundException;
import net.nh.repository.OrganisationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class AccountService {

    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Account createAccount(Account account) throws Exception {
        return accountRepository.create(account);
    }

    public Account updateAccount(Long id, Account account) throws Exception {
        Account existing = accountRepository.findById(id);
        if (existing == null) {
            throw new EntityNotFoundException(Account.class, id);
        }
        account.setId(id);
        return accountRepository.update(account);
    }

    public Account findById(Long id) throws EntityNotFoundException {
        Account account = accountRepository.findById(id);
        if (account == null) {
            throw new EntityNotFoundException(Account.class, id);
        }
        return account;
    }

    public List<Account> findAll() {
        return accountRepository.findAll();
    }

    public List<Account> findByPublisher(Long publisherId) {
        return accountRepository.findByPublisher(publisherId);
    }

}
