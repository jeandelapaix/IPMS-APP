package rw.imps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.imps.domain.Account;
import rw.imps.domain.User;
import rw.imps.repository.AccountRepo;
import rw.imps.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountRepo accountRepo;

    @Override
    public void createAccountProfile(Account account) {
        accountRepo.save(account);
    }

    @Override
    public Account findAccountByUser(User user) {
        return accountRepo.findAccountByUser(user);
    }
}
