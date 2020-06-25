package rw.imps.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.imps.domain.Account;
import rw.imps.domain.AgentTransaction;
import rw.imps.domain.User;
import rw.imps.repository.AgentTransactionRepo;
import rw.imps.service.AgentTransactionService;

import java.util.List;

@Service
@Transactional
public class AgentTransactionServiceImpl implements AgentTransactionService {

    @Autowired
    private AgentTransactionRepo agentTransactionRepo;

    @Override
    public AgentTransaction createTransaction(AgentTransaction agentTransaction) {
        return agentTransactionRepo.save(agentTransaction);
    }

    @Override
    public List<AgentTransaction> viewAllTransactions() {
        return agentTransactionRepo.findAll();
    }

    @Override
    public List<AgentTransaction> agentTransactions(Account user) {
        return agentTransactionRepo.findByPerformedAccount(user);
    }

    @Override
    public List<AgentTransaction> dailyTransactions() {
        return agentTransactionRepo.dailyTransactions();
    }
}
