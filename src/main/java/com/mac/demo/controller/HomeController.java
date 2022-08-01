package com.mac.demo.controller;

import java.util.*;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mac.demo.model.Board;
import com.mac.demo.model.User;
import com.mac.demo.service.HomeService;



@RequestMapping("/home")
@Controller
public class HomeController {
	
	@Autowired 
	private HomeService svc;

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
	public String dataSorce() {
		
		return "thymeleaf/mac/home/dataSource";
	}
	
//	사이트소개
	@GetMapping("/siteIntroduction")
	public String siteIntroduction() {
		
		return "thymeleaf/mac/home/siteIntroduction";
	}
	
	@GetMapping("/myPage/{idMac}")
	public String myPage(@PathVariable("idMac") String idMac, Model model) {

		model.addAttribute("user",svc.getMyPageInUser(idMac));
		List<Board> freeBoard = svc.getMyPageInFreeBoard(idMac);
		model.addAttribute("freeBoard", freeBoard);
		List<Board> adsBoard = svc.getMyPageInAdsBoard(idMac);
		model.addAttribute("adsBoard", adsBoard);
		return "thymeleaf/mac/User/myPage";
	}
	
	
}