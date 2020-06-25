package rw.imps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import rw.imps.domain.Account;
import rw.imps.domain.User;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Account findAccountByUser(User user);
}
