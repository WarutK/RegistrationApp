package com.brownie.userman;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.brownie.userman.UserRegistrationDto;
import com.brownie.userman.User;
import com.brownie.userman.UserService;

@Controller
public class UserRegistrationController {

    @Autowired
    private UserService userService;

    @ModelAttribute("user")
    public UserRegistrationDto userRegistrationDto() {
        return new UserRegistrationDto();
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String showRegistrationForm(Model model) {
    	//System.out.println("Get -> : /registration"); //
        return "html_userman/registration_TP";
    }

    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@ModelAttribute("user") @Valid UserRegistrationDto userDto,
        BindingResult result) {
    	
    	//System.out.println("Post -> : /registration"); //
    	
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

        if (result.hasErrors()) {
            return "html_userman/registration_TP";
        }

        userService.save(userDto);
        return "redirect:/registration?success";
    }
}