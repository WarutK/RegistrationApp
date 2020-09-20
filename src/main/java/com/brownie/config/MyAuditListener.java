package com.brownie.config;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.audit.AuditEvent;
import org.springframework.boot.actuate.audit.listener.AuditApplicationEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.RequestHandledEvent;
import org.springframework.web.context.support.ServletRequestHandledEvent;

import com.brownie.RegistrationApp;
import com.brownie.log.Log;
import com.brownie.log.LogRepository;

//import org.springframework.boot.actuate.audit.listener;

@Component
public class MyAuditListener { 
	
    @Autowired
    private LogRepository logRepository;
    
    private static final Logger logger = LoggerFactory.getLogger(RegistrationApp.class);

    
    @EventListener(condition = "#event.auditEvent.type == 'AUTHENTICATION_SUCCESS'")
    public void onAuthSuccess(AuditApplicationEvent event) {

        AuditEvent auditEvent = event.getAuditEvent();
        WebAuthenticationDetails details = 
                (WebAuthenticationDetails) auditEvent.getData().get("details");
        logger.info("Audit Log: IP: {}, User: {}, Type: {}", details.getRemoteAddress(), auditEvent.getPrincipal(), auditEvent.getType());

        Log log = new Log();
        log.setDateTime(LocalDateTime.now());
        log.setLogtype("Audit");
        log.setType("AUTH_SUCCESS");
        log.setIp(details.getRemoteAddress());
        log.setUsername(auditEvent.getPrincipal());
        log.setCode("");
        log.setMethod("");
        log.setUrl("");
        logRepository.save(log);
        
    }

    @EventListener(condition = "#event.auditEvent.type == 'AUTHENTICATION_FAILURE'")
    public void onAuthFailure(AuditApplicationEvent event) {

        AuditEvent auditEvent = event.getAuditEvent();
        WebAuthenticationDetails details = 
                (WebAuthenticationDetails) auditEvent.getData().get("details");
        logger.info("Audit Log: IP: {}, User: {}, Type: {}", details.getRemoteAddress(), auditEvent.getPrincipal(), auditEvent.getType());
        
        Log log = new Log();
        log.setDateTime(LocalDateTime.now());
        log.setLogtype("Audit");
        log.setType("AUTH_FAILURE");
        log.setIp(details.getRemoteAddress());
        log.setUsername(auditEvent.getPrincipal());
        log.setCode("");
        log.setMethod("");
        log.setUrl("");
        logRepository.save(log);

    }
    
    
    // required HttpSessionEventPublisher
	@EventListener
	public void onDestroy(SessionDestroyedEvent event) {
	    //System.out.println(event);
		
			List<SecurityContext> lstSecurityContext = event.getSecurityContexts();
	        UserDetails ud;
	        for (SecurityContext securityContext : lstSecurityContext)
	        {
	            ud = (UserDetails) securityContext.getAuthentication().getPrincipal();

	            logger.info("Audit Log: IP: {}, User: {}, Type: {}", "-", ud.getUsername(), "SESSION_CLOSED");
	            
	            Log log = new Log();
	            log.setDateTime(LocalDateTime.now());
	            log.setLogtype("Audit");
	            log.setType("SESSION_CLOSED");
	            log.setIp("");
	            log.setUsername(ud.getUsername());
	            log.setCode("");
	            log.setMethod("");
	            log.setUrl("");
	            logRepository.save(log);
	            
	        }
		
	}
    
    @EventListener
    public void handleEvent (RequestHandledEvent e) {
        //System.out.println("-- RequestHandledEvent --");
        //System.out.println(e);
        ServletRequestHandledEvent s = (ServletRequestHandledEvent) e;
        //
        // URL filter
        boolean log_flag = true;
        String[] url_filter = {
        		"/favicon.ico",
        		
        		"/css/main.css",
        		"/js/main.js",
        		
        		"/js/numberAddcommas.js"
        };

        for(int i=0; i < url_filter.length; i++) {
        	if(s.getRequestUrl().compareToIgnoreCase(url_filter[i]) == 0) 
        		log_flag = false;
        	
        }
        
        //
        if(log_flag) logger.info("Access Log: IP: {}, User: {}, Code: {}, Method: {}, URL: {}", s.getClientAddress(), s.getUserName(), s.getStatusCode(), s.getMethod(), s.getRequestUrl());
        
        if(log_flag) {
        Log log = new Log();
        log.setDateTime(LocalDateTime.now());
        log.setLogtype("Access");
        log.setType("Web");
        log.setIp(s.getClientAddress());
        log.setUsername(s.getUserName());
        log.setCode(Integer.toString(s.getStatusCode()));
        log.setMethod(s.getMethod());
        log.setUrl(s.getRequestUrl());
        logRepository.save(log);
        }
        
    }
    
    
}

