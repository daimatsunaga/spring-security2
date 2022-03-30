package com.example.demo.controller;

import static org.hamcrest.CoreMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.transaction.Transactional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.model.SiteUser;
import com.example.demo.util.Role;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class SecurityControllerTest {
	
	@Autowired
	MockMvc mockMvc;

	@Test
	@DisplayName("登録エラーがあるとき、エラーを表示することを期待")
	void whenThereIsRegistrationError_expectToSeeErrors() throws Exception {
		mockMvc
			//リクエストを実行
			.perform(
					post("/register")
					.flashAttr("user", new SiteUser())
					//csrfトークンを自動投入(PostリクエストはCSRF Tokenがないと403エラーが出てしまうため)
					.with(csrf())
				)
				//エラーがあることを検証(modelに正しくエラーあるか)
				.andExpect(model().hasErrors())
				//指定したHTMLを表示しているか検証
				.andExpect(view().name("register"));
	}
	
	@Test
	@DisplayName("管理者ユーザーとして登録する時、成功することを期待")
	void whenRegisteringAsAdminUser_expectToSucceed() throws Exception {
		SiteUser user = new SiteUser();
		user.setUsername("管理者ユーザ");
		user.setPassword("password");
		user.setEmail("admin@example.com");
		user.setGender(0);
		user.setAdmin(true);
		user.setRole(Role.ADMIN.name());
		
		mockMvc
			.perform(post("/register")
			.flashAttr("user",user)
			.with(csrf())
			)
			//エラーがないことを検証
			.andExpect(model().hasNoErrors())
			.andExpect(redirectedUrl("/login?register"))
			//ステータスコードがFound(302)であることを検証(Foundは302を確認するメソッド)
			.andExpect(status().isFound());
	}
	
	@Test
	@DisplayName("管理者ユーザでログイン時、ユーザ一覧を表示することを期待")
	//モックユーザーでログインする springsecurityのテストで一番簡単（引数を取らないで行うことも可能）
	@WithMockUser(username="admin", roles = "ADMIN")
	void whenLoggedInAsAdminUser_expectToSeeListOfUsers() throws Exception {
		mockMvc
			.perform(get("/admin/list"))
			//ステータスコード200であることを検証
			.andExpect(status().isOk())
			//HTMLの表示内容に、指定した文字列を含んでいるか検証
			.andExpect(content().string(containsString("ユーザ一覧")))
			.andExpect(view().name("list"));
	}

}
