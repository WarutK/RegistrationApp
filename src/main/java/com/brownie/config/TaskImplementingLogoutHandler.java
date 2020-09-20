package com.brownie.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import com.brownie.RegistrationApp;
import com.brownie.log.Log;
import com.brownie.log.LogRepository;
import com.brownie.log.LogService;
import com.brownie.log.LogServiceImpl;

public class TaskImplementingLogoutHandler implements LogoutHandler {
	
    @Autowired
    private LogRepository logRepository;
    
    
    /*
    @Autowired
    public void setLogRepository(LogRepository logRepository) {
    	this.logRepository = logRepository;
    }
    */
	
    /*
    private LogService logService;

    public void setLogService(LogService logService) {
        this.logService = logService;
    }
    */
    

	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationApp.class);

    /* (non-Javadoc)
     * @see org.springframework.security.web.authentication.logout.LogoutHandler#logout(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, org.springframework.security.core.Authentication)
     */
	
    @Override
    public void logout(HttpServletRequest req, HttpServletResponse res,
            Authentication authentication) {
        // do whatever you need
    	
    	//req.getRequestURL() -> https://localhost:8443/logout
    	//req.getRequestURI() -> /logout
    	logger.info("Access Log: IP: {}, User: {}, Code: {}, Method: {}, URL: {}", req.getRemoteAddr(), authentication.getName(), res.getStatus(), req.getMethod(), req.getRequestURI());
    	
    	
    	///*
        Log log = new Log();
        log.setDateTime(LocalDateTime.now());
        log.setLogtype("Access");
        log.setType("Web");
        log.setIp(req.getRemoteAddr());
        log.setUsername(authentication.getName());
        log.setCode(Integer.toString(res.getStatus()));
        log.setMethod(req.getMethod());
        log.setUrl(req.getRequestURI());
        logRepository.save(log);
        //
        //logService = new LogServiceImpl();
        //logService.saveLog(log);
        //*/
        
        
    	
    }

} 
