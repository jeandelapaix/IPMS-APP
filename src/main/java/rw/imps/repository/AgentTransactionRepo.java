package rw.imps.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import rw.imps.domain.Account;
import rw.imps.domain.AgentTransaction;
import rw.imps.domain.User;

import java.util.List;

@Repository
public interface AgentTransactionRepo extends JpaRepository<AgentTransaction, Long> {

    /**
     * This is a query to give transactions for agent user type
     */
    public List<AgentTransaction> findByPerformedAccount(Account account);

    /**
     * This is a query to get all transaction done on current day.
     */

    @Query("select t from AgentTransaction t where t.dailyOperationCode.dailyOperationTrackStatus=true ")
    public List<AgentTransaction> dailyTransactions();

}
