package com.blekione.readinglist.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;

import com.blekione.readinglist.repository.ReaderRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

   @Autowired
   private ReaderRepository readerRepository;

   @Override
   protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable().authorizeRequests().antMatchers("/readList/**").hasRole("bob")
            .and().formLogin().permitAll();
   }

   @Override
   protected void configure(AuthenticationManagerBuilder auth) throws Exception {
      auth.userDetailsService(new UserDetailsService() {

         @Override
         public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
            final var userDetails = readerRepository.findByUsername(username);
            if (userDetails != null) {
               return userDetails;
            }
            throw new UsernameNotFoundException("User [" + username + "] not found.");
         }
      }).passwordEncoder(NoOpPasswordEncoder.getInstance());
   }
}
