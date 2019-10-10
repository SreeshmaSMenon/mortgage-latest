package com.ing.ingmortgage.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {


	
	@Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {

		httpSecurity.httpBasic().and().authorizeRequests().antMatchers(HttpMethod.POST,"/login").hasRole("USER").and()
		.csrf().disable().formLogin().permitAll().defaultSuccessUrl("/customers/1/loans");
		
		
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder authentication) throws Exception {
		authentication.inMemoryAuthentication().withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN").and()
				.withUser("user").password(passwordEncoder().encode("user")).roles("USER");
	}
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	        return new BCryptPasswordEncoder();
	    }

}
