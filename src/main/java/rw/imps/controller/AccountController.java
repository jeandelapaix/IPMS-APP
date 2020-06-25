package rw.imps.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import rw.imps.domain.Agent;
import rw.imps.service.AccountService;

import javax.validation.Valid;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping
    @ApiOperation(value = "", authorizations = {@Authorization("jwtToken")})
    public ResponseEntity<?> createAgent(@RequestBody @Valid Agent agent, BindingResult result) throws Exception {
        if (!result.hasErrors()) {
            accountService.createAccountProfile(agent);
            return new ResponseEntity<String>(agent.getAccountName() + " account created successfully", HttpStatus.CREATED);
        }
        throw new Exception("Something went wrong");
    }

}
