package rw.imps.service;

import rw.imps.domain.Account;
import rw.imps.domain.Agent;
import rw.imps.domain.Owner;
import rw.imps.domain.User;
import rw.imps.repository.UserDaoc;
import rw.imps.security.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDaoc userDaoc;

    @Autowired
    private AccountService accountService;

    @Autowired
    private PasswordEncoder bcryptEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userDaoc.findByUsername(userName);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with userName: " + userName);
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
                new ArrayList<>());
    }

    public User save(UserDTO user) throws Exception {
        Account account = null;

        User testEmail = userDaoc.findByEmail(user.getEmail());
        User testUserName = userDaoc.findByUsername(user.getUsername());

//        if (user.getUserType().equalsIgnoreCase("")) {
//            throw new Exception("You should provide type of the user");
//        }
        if (testEmail != null) {
            throw new Exception("The provided email is used by another account");
        }
        if (testUserName != null) {
            throw new Exception("The provided user name is used by another account");
        }

        if (user.getUserType().equalsIgnoreCase("user")||user.getUserType().equals("")) {
            account = new Agent();
        }
        if (user.getUserType().equalsIgnoreCase("admin")) {
            account = new Owner();
        }

        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());
        newUser.setFullName(user.getFullName());
        newUser.setPhone(user.getPhone());
        newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
        User savedUser = userDaoc.save(newUser);
        account.setUser(savedUser);
        account.setAccountName(savedUser.getFullName());
        accountService.createAccountProfile(account);
        return savedUser;
    }
}