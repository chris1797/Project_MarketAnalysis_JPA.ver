package com.mac.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/home")
@Controller
public class HomeController {

//	홈화면
	@GetMapping("")
	public String home() {
		
		return "home/home";
	}
	
//	데이터 출처
	@GetMapping("/dataSource")
	public String dataSorce() {
		
		return "home/dataSource";
	}
	
//	사이트소개
	@GetMapping("/siteIntroduction")
	public String siteIntroduction() {
		
		return "home/siteIntroduction";
	}
}
