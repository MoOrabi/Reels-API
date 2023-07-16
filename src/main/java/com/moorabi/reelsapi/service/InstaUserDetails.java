package com.moorabi.reelsapi.service;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.moorabi.reelsapi.model.AppUser;

import java.util.Collection;
import java.util.stream.Collectors;


public class InstaUserDetails extends AppUser implements UserDetails {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InstaUserDetails(final AppUser appUser) {
        super(appUser);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" +role))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean isAccountNonExpired() {
        return isActive();
    }

    @Override
    public boolean isAccountNonLocked() {
        return isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return isActive();
    }

    @Override
    public boolean isEnabled() {
        return isActive();
    }
}
