package kr.inhatc.spring.login.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class LoginController {
	
	public String principalData(Principal principal) {
		return principal.getName();
	}

	
	@GetMapping("/login/login")
	public void login(Model model,Principal principal) {
		if(principal == null) {
			model.addAttribute("testname","not");
			System.out.println("===========> 유저정보");
		}else {
			System.out.println("============> 유저정보"+ principalData(principal));
			model.addAttribute("testname",principalData(principal));
		}

		log.info("로그인 진행중...");
	}
	
	@GetMapping("/login/accessDenied")
	public void accessDenied() {
		log.info("접근 거부...");
	}
	
	@GetMapping("/login/logout")
	public void logout() {
		log.info("로그아웃...");
	}
	
	@GetMapping("/username")
	@ResponseBody
	public String currentUserName(Principal principal) {
		System.out.println("=============>아이디"+principal.getName());
		return principal.getName();
}

	
	
}
