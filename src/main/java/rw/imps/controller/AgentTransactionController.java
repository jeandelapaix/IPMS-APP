package rw.imps.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rw.imps.domain.Account;
import rw.imps.domain.AgentTransaction;
import rw.imps.service.AgentTransactionService;
import rw.imps.util.IMPSConstant;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/agenttransactions")
public class AgentTransactionController {

    @Autowired
    private AgentTransactionService agentTransactionService;

    @Autowired
    private ServletContext servletContext;

    @PostMapping
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public ResponseEntity<?> createTransaction(@RequestBody @Valid AgentTransaction agentTransaction, BindingResult bindingResult) throws Exception {

        if (bindingResult.hasErrors()) {
            throw new Exception("Something is wrong");
        }
        AgentTransaction agentTransaction1 = agentTransactionService.createTransaction(agentTransaction);
        return new ResponseEntity<String>("Transaction is done successfully", HttpStatus.CREATED);
    }

    @GetMapping
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public List<AgentTransaction> getAllTransactions() {
        return agentTransactionService.viewAllTransactions();
    }

    @GetMapping("/agent")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public List<AgentTransaction> getAgentTransactions() {
        Account loggedAccount = (Account) servletContext.getAttribute(IMPSConstant.LOGGED_ACCOUNT_PROFILE);
        return agentTransactionService.agentTransactions(loggedAccount);
    }

    @GetMapping("/today")
    @ApiOperation(value = "", authorizations = {@Authorization(value = "jwtToken")})
    public List<AgentTransaction> todayTransactions() {
        return agentTransactionService.dailyTransactions();
    }


}
