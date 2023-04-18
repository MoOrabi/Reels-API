package com.moorabi.reelsapi.config;

import com.moorabi.reelsapi.util.JwtRequestFilter;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtRequestFilter jwtRequestFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		
		http
		.csrf()
		.disable()
		.authorizeHttpRequests()
        .antMatchers("/auth/**","/f/**","/home","/room/**")
        .permitAll()
        .anyRequest()
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
		.authenticationProvider(authenticationProvider)
		.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class)
		.logout()
		.logoutUrl("/api/v1/auth/logout")
		.addLogoutHandler(logoutHandler)
		.logoutSuccessHandler(
				(request, response, authentication) -> 
				SecurityContextHolder.clearContext());
		;
		
		return http.build();
	}
    
//	@Bean
//	@Order(1)
//	public SecurityFilterChain oAuthSecurityFilterChain(HttpSecurity http) throws Exception {
//		
//		
//		http
//        .csrf()
//        .disable()
//        .authorizeHttpRequests()
//        .anyRequest()
//        .authenticated()
//        .and()
//        .oauth2Login()
//    ;
//		return http.build();
//	}

   
}