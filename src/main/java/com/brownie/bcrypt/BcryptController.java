package com.brownie.bcrypt;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.ui.Model;

@Controller
public class BcryptController {
	
    @RequestMapping("/bcrypt/new")
    public String newCrypt(Model model){
    	model.addAttribute("bcrypt", new Bcrypt());
        return "html_bcrypt/bcryptform";
    }
    
    @RequestMapping(value = "/bcrypt", method = RequestMethod.GET)
    public String wronggetCrypt(Model model){
        return "redirect:/bcrypt/new";
    }
    
    @RequestMapping(value = "/bcrypt", method = RequestMethod.POST)
    public String showCrypt(@Valid Bcrypt bcrypt, BindingResult bindingResult, Model model){
    	
        //check for errors
        if (bindingResult.hasErrors()) {
        	System.out.println(bindingResult.getFieldErrors().toString());
        	return "html_bcrypt/bcryptform";
        }
      
		BCryptPasswordEncoder enc = new BCryptPasswordEncoder();
		
		bcrypt.setCrypted(enc.encode(bcrypt.getData()));
		System.out.println("Data: " + bcrypt.getData());
		System.out.println("BCrypted: " + bcrypt.getCrypted());

		
        return "html_bcrypt/bcryptshow";
    }

}
