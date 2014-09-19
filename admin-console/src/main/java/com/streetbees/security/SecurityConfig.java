package com.streetbees.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/assets/css/**").permitAll()
                .antMatchers("/assets/fonts/**").permitAll()
                .antMatchers("/assets/ico/**").permitAll()
                .antMatchers("/assets/img/**").permitAll()
                .antMatchers("/assets/js/**").permitAll()
                .antMatchers("/assets/less/**").permitAll()
                .antMatchers("/assets/plugins/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/signin");
    }
}
