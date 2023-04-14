package com.moorabi.reelsapi.util;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.moorabi.reelsapi.repository.TokenRepository;
import com.moorabi.reelsapi.service.JwtUserDetailsService;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUserDetailsService jwtUserDetailsService;
    private final JwtTokenUtil jwtTokenUtil;
    private final TokenRepository tokenRepository;
    

    @Override
    protected void doFilterInternal(
    		HttpServletRequest request, 
    		HttpServletResponse response, 
    		FilterChain chain)
            throws ServletException, IOException {

        final String requestTokenHeader = request.getHeader("Authorization");
        final String jwtToken;
        final String username;
        
        try {
        	if (requestTokenHeader==null||!requestTokenHeader.startsWith("Bearer ")) {
            	chain.doFilter(request, response);
            	logger.warn("JWT is not given or JWT Token does not begin with Bearer String");
            	return;
            }         
            
            jwtToken = requestTokenHeader.substring(7);
            username = jwtTokenUtil.getUsernameFromToken(jwtToken);
            if (StringUtils.isNotEmpty(username)
                    && null == SecurityContextHolder.getContext().getAuthentication()) {
                UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(username);
                var isTokenValid = tokenRepository.findByToken(jwtToken)
                		.map(t -> !t.isExpired() && !t.isRevoked())
                		.orElse(false);
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)
                		&& isTokenValid) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext()
                            .setAuthentication(usernamePasswordAuthenticationToken);
                    
                }
            }
        } catch (IllegalArgumentException e) {
            logger.error("Unable to fetch JWT Token");
        } catch (ExpiredJwtException e) {
            logger.error("JWT Token is expired");
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        
        chain.doFilter(request, response);
    }

}
