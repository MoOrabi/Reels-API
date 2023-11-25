package com.moorabi.reelsapi.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.moorabi.reelsapi.service.UserService;
import com.moorabi.reelsapi.util.JwtRequestFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

	private final JwtRequestFilter jwtRequestFilter;
	private final AuthenticationProvider authenticationProvider;
	private final LogoutHandler logoutHandler;
    private final CustomOAuth2UserService oauth2UserService;
	private final UserService userService;
    
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		
		
		http
		.requiresChannel(channel -> 
        					channel.anyRequest().requiresSecure())
		.cors()
		.configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.setAllowedOrigins (List.of("https://localhost:4200"));
            config.setAllowedMethods (List.of("*"));
            config.setAllowCredentials (true);
            config.setAllowedHeaders (List.of("*"));
            config.setMaxAge (2500L);
            return config;
        })
		.and()
		.csrf()
		.disable()
		
		.authorizeHttpRequests()
		.antMatchers("/auth/**","/auth/logine*","/f/**","/home","/loginf","./css/login","./js/login","/login")
        .permitAll()
        .antMatchers("/api/v1/**")
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
				SecurityContextHolder.clearContext())
		;
		
		
		return http.build();
	}
	
	@Bean
    public OAuth2UserService<OAuth2UserRequest, OAuth2User> oauth2UserService() {
        return new CustomOAuth2UserService();
    }
	
 
   
}