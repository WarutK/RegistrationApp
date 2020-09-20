package com.brownie.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
 
@Controller
public class WebController {
	
	   
    @RequestMapping(value="/")
    public String home(){
        return "home_TP";
    }
   
    @RequestMapping(value="/main")
    public String main_entry(){
        return "main_TP";
    }
    
    @RequestMapping(value="/development")
    public String development(){
        return "development";
    }
      
    
    @RequestMapping(value="/user")
    public String user(){
        return "user_TP";
    }
   
    @RequestMapping(value="/login")
    public String login(){
        return "loginCloud";
    }
   
    @RequestMapping(value="/403")
    public String Error403(){
        return "error_403_TP";
    }
}