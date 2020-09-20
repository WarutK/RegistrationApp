package com.brownie.userman;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.brownie.userman.UserRegistrationDto;
import com.brownie.userman.Role;
import com.brownie.userman.User;
import com.brownie.userman.UserRepository;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    
    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }
    
    public User getUserdByUserName(String username){
        return userRepository.findByUserName(username);
    }
    
    public User findById(Long id) {
    	
    	User existingUser = null;
    	
    	Optional<User> opuser = userRepository.findById(id);
    	if(opuser.isPresent()) {
    		existingUser = opuser.get();
    		//System.out.println("id = " + existingUser.getId());
    		//System.out.println("username = " + existingUser.getUserName());
    	}
    	
        return existingUser;
    }
    
    public User findByEmail(String email){
        return userRepository.findByEmail(email);
    }
    
    public User getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User save(UserRegistrationDto registration){
        User user = new User();
        user.setUserName(registration.getUserName());
        user.setFirstName(registration.getFirstName());
        user.setLastName(registration.getLastName());
        user.setEmail(registration.getEmail());
        user.setPassword(passwordEncoder.encode(registration.getPassword()));
        //-----
        user.setEnabled(false);
        

        String admin_str = "BigBoss"; // First user with ROLE_ADMINISTRATOR
        
        if(user.getUserName().compareToIgnoreCase(admin_str) == 0) {
        	System.out.println("Hi, " + admin_str + "! Welcome back!"); // ROLE_ADMINISTRATOR
        	user.setRoles(Arrays.asList(new Role[] {new Role("ROLE_ADMINISTRATOR"), new Role("ROLE_OPERATOR")}));
        	user.setEnabled(true);
        } else {
        	System.out.println("Hello, " + user.getFirstName() + "!");
        	//user.setRoles(Arrays.asList(new Role("ROLE_USER")));
        	user.setRoles(Arrays.asList(new Role[] {new Role("ROLE_USER")}));
        }
        
        return userRepository.save(user);
    }
    
    
    // Userman - Add
    public User save(UserManDto userman){
        User user = new User();
        user.setUserName(userman.getUserName());
        user.setFirstName(userman.getFirstName());
        user.setLastName(userman.getLastName());
        user.setEmail(userman.getEmail());
        user.setPassword(passwordEncoder.encode(userman.getPassword()));
        //-----
        if(userman.getEnabled()) user.setEnabled(true);
        else user.setEnabled(false);
        
        List<Role> list_role = new ArrayList<Role>();
        
        if(userman.getRoleAdministrator()) list_role.add(new Role("ROLE_ADMINISTRATOR"));
        if(userman.getRoleOperator()) list_role.add(new Role("ROLE_OPERATOR"));
        if(userman.getRoleUser()) list_role.add(new Role("ROLE_USER"));
        
        user.setRoles(list_role);

        //-----
        
        return userRepository.save(user);
    }
    
    // Userman - Edit
    public User save(Long id, UserManEditDto usermanedit) {
        User user = findById(id);
        //user.setId(id);
        user.setUserName(usermanedit.getUserName());
        user.setFirstName(usermanedit.getFirstName());
        user.setLastName(usermanedit.getLastName());
        user.setEmail(usermanedit.getEmail());
        //user.setPassword(password);
        //-----
        if(usermanedit.getEnabled()) user.setEnabled(true);
        else user.setEnabled(false);
        
        List<Role> list_role = new ArrayList<Role>();
        
        if(usermanedit.getRoleAdministrator()) list_role.add(new Role("ROLE_ADMINISTRATOR"));
        if(usermanedit.getRoleOperator()) list_role.add(new Role("ROLE_OPERATOR"));
        if(usermanedit.getRoleUser()) list_role.add(new Role("ROLE_USER"));
        
        user.setRoles(list_role);

        //-----
        
        return userRepository.save(user);
    }
    
    // Userman - Passwd
    public User save(Long id, UserManPasswdDto usermanpasswd) {
        User user = findById(id);
        //user.setId(id);
        //user.setUserName(usermanpasswd.getUserName());
        //user.setFirstName(usermanpasswd.getFirstName());
        //user.setLastName(usermanpasswd.getLastName());
        //user.setEmail(usermanpasswd.getEmail());
        user.setPassword(passwordEncoder.encode(usermanpasswd.getPassword()));
        //-----

        //-----
        
        return userRepository.save(user);
    }


    // UserName
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUserName(username);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(),
                user.getPassword(),
                user.isEnabled(),
                true,true,true, // accountNonExpired, credentialsNonExpired, accountNonLocked
                mapRolesToAuthorities(user.getRoles()));
    }
    

	// E-mail
    @Override
    public UserDetails loadUserByEmail(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null){
            throw new UsernameNotFoundException("Invalid username or password.");
        }
        return new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,true,true, // accountNonExpired, credentialsNonExpired, accountNonLocked
                mapRolesToAuthorities(user.getRoles()));
    }

    

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
    
    
    public List<User> findAll(){ // Find All
        return userRepository.findAll();
    }
    
    public List<User> findAllByOrderByUserNameAsc(){ // Find All by Order by userName asc
        return userRepository.findAllByOrderByUserNameAsc();
    }
    
    // Test
    public User createUser(User u){
        return userRepository.save(u);
    }
    
    @Override
    public void deleteUser(Long id) {
    	userRepository.deleteById(id);
    }
    
    @Override
    public void enabledUser(Long id) {
    	userRepository.enabledUser(id);
    }
    
    @Override
    public void disabledUser(Long id) {
    	userRepository.disabledUser(id);
    }
    
}
