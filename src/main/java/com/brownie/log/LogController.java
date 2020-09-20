package com.brownie.log;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.ui.Model;

import java.time.LocalDateTime;

@Controller
public class LogController {
	

    
    @Autowired
    private LogRepository logRepository;
    
    @Autowired
    private LogPageRepository logPageRepository;
    
    
    //Read: Pageable
    @RequestMapping(value = "/logspage", method = RequestMethod.GET)
    public String pageLog(@PageableDefault(size = 10) Pageable pageable, Model model){
    	Page<Log> page_log = logPageRepository.findAll(pageable);
    	model.addAttribute("page", page_log);
    	
    	//
    	int maxDispPage = 10; // even number
    	int totalPage = page_log.getTotalPages();
    	
    	int currDispPage = page_log.getNumber() + 1;
    	
    	int sPage = 0;
    	int ePage = 0;
    	
    	// set start page relative to selected  
        sPage = currDispPage - (maxDispPage / 2);
        // adjust for first pages   
        sPage = Math.max(sPage, 1);
        // set end page relative to start    
        ePage = sPage + maxDispPage - 1;
        // adjust start and end for last pages     
        if (ePage > totalPage) {
        	ePage = totalPage;
            sPage = ePage - maxDispPage + 1;
            sPage = Math.max(sPage, 1);
        }
        
        //System.out.println("currDispPage: " + currDispPage);
    	
    	model.addAttribute("sPage", sPage);
    	model.addAttribute("ePage", ePage);
    	//
    	
        return "html_log/log_page3";
    }
	

    //Read: List All
    @RequestMapping(value = "/logs", method = RequestMethod.GET)
    public String listLog(Model model){
    	//List<Log> list_log = logRepository.findAll();
    	List<Log> list_log = logRepository.findAllByOrderByIdDesc();
    	model.addAttribute("logs", list_log);
        return "html_log/log_TP";
    }
    
    //Read: List All
    @RequestMapping(value = "/logs1", method = RequestMethod.GET)
    public String listLog1(Model model){
    	//List<Log> list_log = logRepository.findAll();
    	List<Log> list_log = logRepository.findAllByOrderByIdDesc();
    	model.addAttribute("logs", list_log);
        return "html_log/log1_TP";
    }
   
}
