package rw.imps.controller;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rw.imps.domain.Account;
import rw.imps.domain.User;
import rw.imps.security.JwtRequest;
import rw.imps.security.JwtResponse;
import rw.imps.security.UserDTO;
import rw.imps.securityconfig.JwtTokenUtil;
import rw.imps.service.AccountService;
import rw.imps.service.JwtUserDetailsService;
import rw.imps.service.UserService;
import rw.imps.util.IMPSConstant;

import javax.servlet.ServletContext;
import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/users")
public class JwtAuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AccountService accountService;

    @Autowired
    private UserService userService;

    @Autowired
    private ServletContext servletContext;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody @Valid JwtRequest authenticationRequest) throws Exception {

        authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        final User u = userService.findByUsername(authenticationRequest.getUsername());
        final Account acc = accountService.findAccountByUser(u);
        final String token = jwtTokenUtil.generateToken(userDetails);

        // Add the Logged User to the Servelet Session
        servletContext.setAttribute(IMPSConstant.LOGGED_ACCOUNT_PROFILE, acc);

        return ResponseEntity.ok(new JwtResponse(token, acc));
        // return ResponseEntity.ok(token);
    }

    @GetMapping("/logmeout")
    public void logout() {
        try {
            servletContext.removeAttribute(IMPSConstant.LOGGED_ACCOUNT_PROFILE);
            //return ResponseEntity.ok("Success");
        } catch (Exception e) {
            //	return ResponseEntity.ok(e.getMessage());
            e.printStackTrace();
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> saveUser(@RequestBody @Valid UserDTO user) throws Exception {
            User user1 = userDetailsService.save(user);
            System.out.println(user1);
            return new ResponseEntity<User>(user1, HttpStatus.OK);
    }

    private void authenticate(String userName, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @GetMapping("/testing")
    public String testing() {
        return "Lea Iradukunda";
    }

    @GetMapping("/all")
    public List<User> allUsers(){
        return userService.findAll();
    }
}