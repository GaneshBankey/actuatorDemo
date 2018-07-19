package com.demo.actuator.actuatorDemo;

import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class ActuatorSecurity extends WebSecurityConfigurerAdapter {
  public void configure(WebSecurity web) throws Exception {
	  web.ignoring().antMatchers("/actuator/info","actuator/health");
  }
	
  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http.requestMatcher(EndpointRequest.toAnyEndpoint()).authorizeRequests()
      .anyRequest().hasRole("ADMIN")
      .and()
      .httpBasic();
  }
  
  @Bean
  @Override
  public UserDetailsService userDetailsService() {
      UserDetails user =
          User.withDefaultPasswordEncoder()
              .username("admin")
              .password("admin")
              .roles("ADMIN")
              .build();
      return new InMemoryUserDetailsManager(user);
  }
}