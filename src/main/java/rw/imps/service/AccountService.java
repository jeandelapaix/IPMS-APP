package rw.imps.service;

import rw.imps.domain.Account;
import rw.imps.domain.User;

public interface AccountService {
    void createAccountProfile(Account account);
    Account findAccountByUser(User user);
}
