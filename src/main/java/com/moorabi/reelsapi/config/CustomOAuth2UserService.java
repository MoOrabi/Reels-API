package com.moorabi.reelsapi.config;

import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.moorabi.reelsapi.model.AppUser;
import com.moorabi.reelsapi.model.LoginMethodEnum;
 
@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService  {
 
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();

        // Extract user attributes from the OAuth2 provider's response
        String email = (String) attributes.get("email");
        String username = (String) attributes.get("username");
        String firstName = (String) attributes.get("given_name");
        String lastName = (String) attributes.get("family_name");

        // Customize how you map the OAuth2 attributes to your User object
        // For simplicity, let's assume you have a User class with username, email, firstName, and lastName fields
        AppUser customUser = new AppUser();
        customUser.setEmail(email);
        customUser.setFirstName(firstName);
        customUser.setLastName(lastName);
        customUser.setUsername(username);
        
        

        return (OAuth2User) customUser;
    }
 
}
