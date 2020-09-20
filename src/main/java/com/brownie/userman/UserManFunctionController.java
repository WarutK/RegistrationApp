package com.brownie.userman;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brownie.userman.UserService;

import org.springframework.ui.Model;

import java.time.LocalDateTime;

@Controller
public class UserManFunctionController {
	
    @Autowired
    private UserService userService;
    
    @ModelAttribute("user")
    public UserManDto userManDto() {
        return new UserManDto();
    }
    
    @ModelAttribute("useredit")
    public UserManEditDto userManEditDto() {
        return new UserManEditDto();
    }
    
    @ModelAttribute("userpasswd")
    public UserManPasswdDto userManPasswdDto() {
        return new UserManPasswdDto();
    }
    
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
      
    
    //Enabled
    @RequestMapping("/userman_enabled/{id}")
    public String enabledUser(@PathVariable Long id){
    	userService.enabledUser(id); // *** Enabled ***
        return "redirect:/userman";
    }
    
    //Disabled
    @RequestMapping("/userman_disabled/{id}")
    public String disabledUser(@PathVariable Long id){
    	userService.disabledUser(id); // *** Disabled ***
        return "redirect:/userman";
    }
    
    
    //Delete
    @RequestMapping("/userman_del/{id}")
    public String deleteUser(@PathVariable Long id){
    	userService.deleteUser(id); // *** Delete ***
        return "redirect:/userman";
    }
    
    
    // Add - get
    @RequestMapping(value = "/userman_add", method = RequestMethod.GET)
    public String usermanAddForm(Model model) {
    	//System.out.println("Get -> : /userman_add"); //
        return "html_userman/usermanadd_TP";
    }
    
    // Add - post
    @RequestMapping(value = "/userman_add", method = RequestMethod.POST)
    public String usermanAddAccount(@ModelAttribute("user") @Valid UserManDto userDto,
        BindingResult result) {
    	
    	//System.out.println("Post -> : /userman_add"); //
    	
        User existing = userService.findByUserName(userDto.getUserName());
        if (existing != null) {
            result.rejectValue("userName", null, "There is already an account registered with that username");
        }

    	/*
        User existing = userService.findByEmail(userDto.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        */
        
        // Roles selected
        boolean role_selected = false;
        if(userDto.getRoleUser() || userDto.getRoleOperator() || userDto.getRoleAdministrator()) role_selected = true;

        if (!role_selected) {
            result.rejectValue("roleUser", null, "At least one selected");
        }
        

        if (result.hasErrors()) {
            return "html_userman/usermanadd_TP";
        }

        userService.save(userDto);
        return "redirect:/userman";
    }
    
    
    // Edit - get
    @RequestMapping(value = "/userman_edit/{id}", method = RequestMethod.GET)
    public String usermanEditForm(@PathVariable Long id, Model model) {
    	//System.out.println("Get -> : /userman_edit"); //
    	
    	User user = userService.findById(id);
        if (user == null) {
            return "redirect:/userman"; // Error: id not found
        }
    	UserManEditDto usermanedit = null;
    	
    	if(user != null) {
    		usermanedit = new UserManEditDto();
    		usermanedit.setId(user.getId());
    		usermanedit.setUserName(user.getUserName());
    		usermanedit.setFirstName(user.getFirstName());
    		usermanedit.setLastName(user.getLastName());
    		usermanedit.setEmail(user.getEmail());
    		//usermanedit.setPassword(user.getPassword());
    		//-----
    		usermanedit.setEnabled(user.isEnabled());
    		
    		for (Role e: user.getRoles()) {
    			String role_name = e.getName();
    			if(role_name.compareTo("ROLE_ADMINISTRATOR") == 0) usermanedit.setRoleAdministrator(true);
    			if(role_name.compareTo("ROLE_OPERATOR") == 0) usermanedit.setRoleOperator(true);
    			if(role_name.compareTo("ROLE_USER") == 0) usermanedit.setRoleUser(true);
    		}
    		
    	}
    	
    	model.addAttribute("useredit", usermanedit);
    	
        return "html_userman/usermanedit_TP";
    }
    
    
    
    // Edit - post
    @RequestMapping(value = "/userman_edit/{id}", method = RequestMethod.POST)
    public String usermanEditAccount(@PathVariable Long id, @ModelAttribute("useredit") @Valid UserManEditDto userDto,
        BindingResult result) {
    	
    	//System.out.println("Post -> : /userman_edit"); //
    	
    	User user = userService.findById(id);
        if (user == null) {
            return "redirect:/userman"; // Error: id not found
        }

    	if(user.getUserName().compareToIgnoreCase(userDto.getUserName()) == 0) { // username not changed
    		System.out.println("username not changed"); //
    	} else {
    		
        	User existing = userService.findByUserName(userDto.getUserName());
        	if (existing != null) {
        		result.rejectValue("userName", null, "There is already an account registered with that username");
        	}
    		
    	}
    	
        
        // Roles selected
        boolean role_selected = false;
        if(userDto.getRoleUser() || userDto.getRoleOperator() || userDto.getRoleAdministrator()) role_selected = true;

        if (!role_selected) {
            result.rejectValue("roleUser", null, "At least one selected");
        }
        

        if (result.hasErrors()) {
            return "html_userman/usermanedit_TP";
        }

        //System.out.println("before usermanEditAccount::save"); //
        userService.save(id, userDto);
        return "redirect:/userman";
    }
    
    
    // Passwd - get
    @RequestMapping(value = "/userman_passwd/{id}", method = RequestMethod.GET)
    public String usermanPasswdForm(@PathVariable Long id, Model model) {
    	//System.out.println("Get -> : /userman_passwd"); //
    	
    	User user = userService.findById(id);
        if (user == null) {
            return "redirect:/userman"; // Error: id not found
        }
    	UserManPasswdDto usermanpasswd = null;
    	
    	if(user != null) {
    		usermanpasswd = new UserManPasswdDto();
    		usermanpasswd.setId(user.getId());
    		usermanpasswd.setUserName(user.getUserName());
    	}
    	
    	model.addAttribute("userpasswd", usermanpasswd);
    	
        return "html_userman/usermanpasswd_TP";
    }
    
    // Passwd - post
    @RequestMapping(value = "/userman_passwd/{id}", method = RequestMethod.POST)
    public String usermanPasswdAccount(@PathVariable Long id, @ModelAttribute("userpasswd") @Valid UserManPasswdDto userDto,
        BindingResult result) {
    	
    	//System.out.println("Post -> : /userman_passwd"); //
    	
    	User user = userService.findById(id);
        if (user == null) {
            return "redirect:/userman"; // Error: id not found
        }
        
        if (result.hasErrors()) {
            return "html_userman/usermanpasswd_TP";
        }

        //System.out.println("before usermanPasswdAccount::save"); //
        userService.save(id, userDto);
        return "redirect:/userman";
    }
    

}
