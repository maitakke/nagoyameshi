package com.example.nagoyameshi.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/css/**", "/images/**", "/js/**", "/storage/**", "/", "/signup/**").permitAll()
				.anyRequest().authenticated())
			.formLogin((form) -> form
				.loginPage("/login") // ログインページのURL
				.loginProcessingUrl("/login") // ログインフォームの送信先URL
				.defaultSuccessUrl("/?loggedIn") // ログイン成功時のリダイレクト先URL
				.failureUrl("/login?error") // ログイン失敗時のリダイレクト先URL
				.permitAll())
			.logout((logout) -> logout
				.logoutSuccessUrl("/?loggedOut") // ログアウト時のリダイレクト先URL
				.permitAll());
		return http.build();
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
