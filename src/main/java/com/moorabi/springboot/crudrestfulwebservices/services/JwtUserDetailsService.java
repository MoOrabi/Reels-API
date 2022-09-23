//package com.moorabi.springboot.crudrestfulwebservices.services;
//
//import java.util.ArrayList;
//
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//public class JwtUserDetailsService implements UserDetailsService {
//
//	@Override
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		if ("MohammedOrabi".equals(username)) {
//			return new User("MohammedOrabi", "eyJhbGciOiJIUzI1NiJ9.eyJJc3N1ZXIiOiJJc3N1ZXIiLCJVc2VybmFtZSI6Ik1vaGFtbWVkT3JhYmkiLCJpYXQiOjE2NjM5MTkyMDJ9.zgLjzNgSHY6mpL5tLqLwgJrpmbEh_PF4fF_1ES2M4Cg",
//					new ArrayList<>());
//		} else {
//			throw new UsernameNotFoundException("User not found with username: " + username);
//		}
//	}
//}