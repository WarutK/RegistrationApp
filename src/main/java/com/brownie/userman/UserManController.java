package com.brownie.userman;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.brownie.userman.UserRegistrationDto;
import com.brownie.userman.User;
import com.brownie.userman.UserService;

@Controller
@RequestMapping("/userman")
public class UserManController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @GetMapping
    public String showRegistrationForm(Model model) {
    	
    	//System.out.println("Get -> : /userman"); //
    	
    	//List<User> lstuser = userService.findAll();
    	List<User> lstuser = userService.findAllByOrderByUserNameAsc();
    	model.addAttribute("users", lstuser);
    	
        return "html_userman/userman_TP";
    }
    
    
}