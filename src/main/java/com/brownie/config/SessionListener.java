package com.brownie.config;


import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.brownie.RegistrationApp;

//@WebListener
public class SessionListener implements HttpSessionListener {
	
	boolean debug = false;
	//boolean debug = true;
	
    //protected static Logger logger = Logger.getLogger("controller");
    private static final Logger logger = LoggerFactory.getLogger(RegistrationApp.class);
    
    //private HttpSession session = null;
    
    
    //@Override
    public void sessionCreated(HttpSessionEvent event)
    {
        // no need to do anything here as connection may not have been established yet
    	HttpSession session  = event.getSession();
        if(debug) logger.info("Session created for id " + session.getId());
    }
 
    //@Override
    public void sessionDestroyed(HttpSessionEvent event)
    {
    	HttpSession session  = event.getSession();
 
        try
        {
        	if(debug) logger.info("Session destroyed for id " + session.getId());
        }
        catch (Exception e)
        {
        	if(debug) logger.info("SesssionListener.sessionDestroyed Unable to obtain Connection", e);
        }
    }
    

    
}
