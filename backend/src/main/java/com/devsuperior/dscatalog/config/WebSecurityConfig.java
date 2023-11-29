package com.devsuperior.dscatalog.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	//aula 03-22
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; //só injetamos porque o bean foi criado na classe AppConfig

	@Autowired
	private UserDetailsService userDetailsService;

	@Override //aula 03-22 1205 1335
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		//web.ignoring().antMatchers("/**"); //até aula 03-22 1111
		web.ignoring().antMatchers("/actuator/**"); //continua liberando todos os caminhos da aplicação, mas usamos a biblioteca actuator usada pelo Spring Cloud OAuth.
	}

	@Override //aula 03-22 1432 1453 1503
	@Bean     //           1511  
	protected AuthenticationManager authenticationManager() throws Exception {		
		return super.authenticationManager();
	}

	
}