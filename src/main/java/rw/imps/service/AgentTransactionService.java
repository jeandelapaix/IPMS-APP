package rw.imps.service;

import rw.imps.domain.Account;
import rw.imps.domain.AgentTransaction;
import rw.imps.domain.User;

import java.util.List;

public interface AgentTransactionService {

    public AgentTransaction createTransaction(AgentTransaction agentTransaction);
    public List<AgentTransaction> viewAllTransactions();
    public List<AgentTransaction> agentTransactions(Account account);
    public List<AgentTransaction> dailyTransactions();
}
