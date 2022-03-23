package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.util.Role;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final UserDetailsService userDetailsService;
	
	public SecurityConfig(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}

    @Bean
    public PasswordEncoder passwordEncoder() {
    	// パスワードの暗号化用に、BCrypt（ビー・クリプト）を使用します
        return new BCryptPasswordEncoder();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // セキュリティ設定を、無視（ignoring）するパスを指定します
        // 通常、cssやjs、imgなどの静的リソースを指定します
        web.ignoring().antMatchers("/js/**", "/css/**", "/img/**", "/webjars/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                // 「/login」と「/register」を全てにアクセス可能にします
                .antMatchers("/login", "/register").permitAll()
                //「/admin」はADMINユーザーだけアクセス可能
                .antMatchers("/admin/**").hasRole(Role.ADMIN.name())
                //それ以外は用認証
                .anyRequest().authenticated()
                .and()
            .formLogin()
                // ログイン時のURLを指定
                .loginPage("/login")
                // 認証後にリダイレクトする場所を指定
                .defaultSuccessUrl("/")
                .and()
            // ログアウトの設定
            .logout()
                // ログアウト時のURLを指定
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .and()
            // Remember-Meの認証を許可します
            // これを設定すると、ブラウザを閉じて、
            // 再度開いた場合でも「ログインしたまま」にできます
            .rememberMe().tokenValiditySeconds(30);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {
    	//ここよくわからん
    	//userDetailServiceを使用して、DBからユーザーを参照する
    	auth.userDetailsService(userDetailsService)
    	.passwordEncoder(passwordEncoder());
    }
}
