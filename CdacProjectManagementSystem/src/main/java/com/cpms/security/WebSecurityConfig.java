package com.cpms.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.cpms.security.jwt.AuthEntryPointJwt;
import com.cpms.security.jwt.AuthTokenFilter;
import com.cpms.services.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)					//TODO Check this annotation
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Autowired
	private AuthTokenFilter authTokenFilter;

	@Autowired
	private AuthEntryPointJwt unauthorizedHandler;

	//Autowired AuthTokenFilter Instead of calling a method for creating instance. @Autowired will create bean. 
//	@Bean
//	public AuthTokenFilter authenticationJwtTokenFilter() {
//		return new AuthTokenFilter();
//	}
	
	@Override
	public void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
	}

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean					//Default BCrypt is used for password encoding.
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	//TODO Check each endpoint and its assigned role to secure every api endpoints 
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable()
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers("/admin/teamsize/**").hasAnyRole("ADMIN", "STUDENT")
			.antMatchers("/admin/**").hasRole("ADMIN")
//			.antMatchers("/user/register").hasRole("ADMIN")
			.antMatchers("/guide/**").hasAnyRole("GUIDE","ADMIN")
	        .antMatchers("/student/**").hasAnyRole("STUDENT","ADMIN")
			//.antMatchers("/student/**").permitAll()
			.antMatchers("/user/**").permitAll()
	        .antMatchers("/").hasAnyRole("ADMIN","GUIDE","STUDENT")
	        .antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**").permitAll().anyRequest().authenticated();
//			.and()
//			.formLogin().loginPage("/user/login").permitAll();
		
		 // If a user try to access a resource without having enough permissions
//		http.exceptionHandling().accessDeniedPage("/user/login");
		http.addFilterBefore(authTokenFilter, UsernamePasswordAuthenticationFilter.class);
	}
}
