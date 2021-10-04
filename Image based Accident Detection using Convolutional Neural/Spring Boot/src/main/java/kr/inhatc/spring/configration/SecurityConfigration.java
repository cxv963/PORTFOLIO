package kr.inhatc.spring.configration;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity //웹 시큐리티를 쓰려면 넣어야함.
public class SecurityConfigration extends WebSecurityConfigurerAdapter{
	//ctrl + alt +shift 추상클래스의 추상 메소드 생성
	@Override
	protected void configure(HttpSecurity security) throws Exception {
	
		//모든 사용자
		security.authorizeRequests().antMatchers("/" ).permitAll();																//권한에 대한 리퀘스트 :: 루트는 누구든지 들어오게 만들겠다.
		
		//권한에 따른 이용
		security.authorizeRequests().antMatchers("/user/**").hasRole("MEMBER");									//권한에 대한 리퀘스트 :: 유저게시판은 어드민만 접근하게 만들겠다.
		security.authorizeRequests().antMatchers("/board/**").hasAnyRole("MEMBER","ADMIN");			//권한에 대한 리퀘스트 :: 보드게시판은 어드민, 멤버만 접근하게 만들겠다.
		security.csrf().disable(); 																															//변조방지 :: 레스트풀을 사용하기 위해서는 비활성화 해야함.
		
		//security.formLogin();																															//로그인 페이지 :: 기본 페이지
		
		security.formLogin().loginPage("/login/login").defaultSuccessUrl("/", true);										//로그인 페이지 부르기 :: 로그인 관련 페이지와 성공시 이동할 페이지
		security.exceptionHandling().accessDeniedPage("/login/accessDenied");											//엑세스가 실패했을 때 이동할 페이지
		security.logout().logoutUrl("/login/logout").invalidateHttpSession(true).logoutSuccessUrl("/");		//로그아웃을 요청하면 세션을 강제 종료하고 인덱스 페이지로 이동
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder();											//패스워드에 암호화 처리 :: 암호화 처리
	}
}
