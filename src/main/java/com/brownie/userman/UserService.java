package com.brownie.userman;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.brownie.userman.UserRegistrationDto;
import com.brownie.userman.User;

public interface UserService extends UserDetailsService {

	User findById(Long id);
    User findByEmail(String email);
    User getUserByEmail(String email);
    
    User findByUserName(String username);
    User getUserdByUserName(String username);

    User save(UserRegistrationDto registration);
    User save(UserManDto userman);
    User save(Long id, UserManEditDto usermanedit);
    User save(Long id, UserManPasswdDto usermanpasswd);
    
    UserDetails loadUserByUsername(String username);
    UserDetails loadUserByEmail(String email);
    
    List<User> findAll();
    List<User> findAllByOrderByUserNameAsc();
    
    User createUser(User u);
    
    void deleteUser(Long id);
    void enabledUser(Long id);
    void disabledUser(Long id);
    	
    
}