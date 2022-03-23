package com.example.demo.service;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
	// Spring Securityではユーザーデータを扱うために「UserDetailsService」というインターフェースが用意されている
	private final SiteUserRepository userRepository;
	
	public UserDetailsServiceImpl(SiteUserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SiteUser user = userRepository.findByUsername(username);
		if(Objects.isNull(user)) {
			throw new UsernameNotFoundException(username + "not found");
		}
		return createUserDetails(user);
	}
	
	public User createUserDetails(SiteUser user) {
		//GrantedAuthority（付与された権限）は、「ROLE_ADMIN」や「ROLE_USER」など、頭に「ROLE_」を付けた文字列を渡します。
		//要素の重複を許可しない集合構造を表します。また、順番も持ちません。数学での集合にもよく似た構造と考えて良いでしょう
		//Listクラスとの違いはListクラスは値を順序通りに並べて格納するのに対しSetクラスは値を順序があるとは限らず格納します。
		//また、Listクラスは同じ値を持つことができますが、Setクラスは同じ値を持つことはできません。
		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
		return new User(user.getUsername(), user.getPassword(), grantedAuthorities);
	}
	
}
