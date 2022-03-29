package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.SiteUser;
import com.example.demo.repository.SiteUserRepository;
import com.example.demo.util.Role;
//publicがない
@SpringBootTest
@Transactional
class UserDetailsServiceImplTest {
	
	@Autowired
	SiteUserRepository repository;
	
	@Autowired
	UserDetailsServiceImpl service;
	
	//publicがない
	@Test
	@DisplayName("ユーザー名が存在する時、ユーザー詳細を取得することを期待します")
	void whenUsernameExists_expectToGetUserDetails() {
		//準備
		SiteUser user = new SiteUser();
		user.setUsername("test");
		user.setPassword("password");
		user.setEmail("test@test.com");
		user.setRole(Role.USER.name());
		repository.save(user);
		
		//実行
		UserDetails actual = service.loadUserByUsername("test");
		
		//検証
		assertEquals(user.getUsername(), actual.getUsername(), "ケース１失敗");
	}
	
	@Test
	@DisplayName("ユーザー名が存在しないとき、例外をスローする")
	void whenUsernameDoesNotExsist_throwException() {
		//実行、検証
		assertThrows(UsernameNotFoundException.class, 
				() -> service.loadUserByUsername("takeda"));
	}

}
