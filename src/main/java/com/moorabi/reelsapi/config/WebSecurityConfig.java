package com.moorabi.reelsapi.config;

import com.moorabi.reelsapi.service.UserService;
import com.moorabi.reelsapi.util.JwtRequestFilter;

import lombok.RequiredArgsConstructor;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

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
		.cors()
		.and()
		.csrf()
		.disable()
		.sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
		.authorizeHttpRequests()
		.antMatchers("/auth/**","/auth/logine*","/f/**","/home","/loginf","./css/login","./js/login","/login")
        .permitAll()
        .antMatchers(HttpMethod.POST, "/facebook/signin").permitAll()
        .antMatchers("/api/v1/**")
        .authenticated()
        
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
//        .loginPage("/loginf")
//        .userInfoEndpoint()
//        .userService(oauth2UserService)
//        .and()
//        .successHandler((AuthenticationSuccessHandler) new AuthenticationSuccessHandler() {
//        	 
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
//                    Authentication authentication) throws IOException, ServletException {
//     
//                CustomOAuth2User oauthUser = (CustomOAuth2User) authentication.getPrincipal();
//     
//                userService.processOAuthPostLogin(oauthUser.getAttribute("username") ,oauthUser.getEmail());
//     
//                response.sendRedirect("/api/v1/reels");
//            }
//
//			
//        })
//    ;
//		return http.build();
//	}

	@Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

   
}