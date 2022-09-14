package com.mac.demo.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@RequestMapping("/home")
@Controller
public class HomeController {
	
//	홈화면
	@GetMapping("")
	public String home(Model model,HttpSession session) {
		
		if(session.getAttribute("idMac")!=null) {
			String uid = session.getAttribute("idMac").toString();
			model.addAttribute("idMac",uid);
			return "thymeleaf/mac/home/home";
		}
		
		return "thymeleaf/mac/home/home";
	}
	
//	데이터 출처
	@GetMapping("/dataSource")
	public String dataSorce(Model model,HttpSession session) {
		if(session.getAttribute("idMac")!=null) {
			String uid = session.getAttribute("idMac").toString();
			model.addAttribute("idMac",uid);
			return "thymeleaf/mac/home/dataSource";
		}
		
		return "thymeleaf/mac/home/dataSource";
	}
	
//	사이트소개
	@GetMapping("/siteIntroduction")
	public String siteIntroduction(Model model,HttpSession session) {
		if(session.getAttribute("idMac")!=null) {
			String uid = session.getAttribute("idMac").toString();
			model.addAttribute("idMac",uid);
			return "thymeleaf/mac/home/siteIntroduction";
		}
		return "thymeleaf/mac/home/siteIntroduction";
	}
	

	
	
}