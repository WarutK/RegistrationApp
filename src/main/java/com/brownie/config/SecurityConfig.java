package com.brownie.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.brownie.userman.UserService;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            
            .antMatchers("/", "/home").permitAll()
            
			.antMatchers("/userman/**").hasAnyRole("ADMINISTRATOR", "OPERATOR")
						
            .antMatchers("/userman_enabled/**").hasRole("ADMINISTRATOR")
            .antMatchers("/userman_disabled/**").hasRole("ADMINISTRATOR")
            .antMatchers("/userman_del/**").hasRole("ADMINISTRATOR")
            .antMatchers("/userman_add/**").hasRole("ADMINISTRATOR")
            .antMatchers("/userman_edit/**").hasRole("ADMINISTRATOR")
            .antMatchers("/userman_passwd/**").hasRole("ADMINISTRATOR")
			
			.antMatchers("/development/**").hasRole("ADMINISTRATOR")
			.antMatchers("/bcrypt/**").hasRole("ADMINISTRATOR")
			.antMatchers("/logs/**").hasAnyRole("ADMINISTRATOR")
			.antMatchers("/logs1/**").hasAnyRole("ADMINISTRATOR")
			
			.antMatchers("/logspage/**").hasAnyRole("ADMINISTRATOR")
            
            .antMatchers(
                "/registration**",
                "/js/**",
                "/css/**",
                "/img/**",
                "/webjars/**").permitAll()

            .anyRequest().authenticated()
            
            .and()
            .formLogin()
            	.loginPage("/login").permitAll()
            	.and()
            	.logout()
            		.invalidateHttpSession(true)
            		.clearAuthentication(true)
            		.addLogoutHandler(taskImplementingLogoutHandler())
            		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
            		.logoutSuccessUrl("/login?logout")
            		.permitAll();
        
		http.exceptionHandling().accessDeniedPage("/403");
		http.formLogin().defaultSuccessUrl("/main", true);
		
		http.exceptionHandling().authenticationEntryPoint(new AjaxAwareAuthenticationEntryPoint("/login"));
        
		http.csrf().disable();
		http.headers().frameOptions().disable();
        
    }
    
	@Bean
	public TaskImplementingLogoutHandler taskImplementingLogoutHandler() {
	    return new TaskImplementingLogoutHandler();
	}

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(userService);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }
}

