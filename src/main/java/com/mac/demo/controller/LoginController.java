package com.mac.demo.controller;

import com.mac.demo.dto.MemberDTO;
import com.mac.demo.serviceImpl.LoginServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessagingException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;


@Slf4j
@RequestMapping("/login")
@RestController
@RequiredArgsConstructor
public class LoginController {
	
	@Autowired
	private final LoginServiceImpl loginSvc;

	
	@GetMapping("/loginForm")
	public String login() {
		return "/login/loginForm";
	}
	
//	세션에 id저장
	@PostMapping("/loginForm")
	public ModelAndView login(@RequestParam("idMac")String idMac, @RequestParam("pwMac")String pwMac, MemberDTO memberDTO, HttpSession session, Model model){

		ModelAndView mav = new ModelAndView("/login/loginForm");
//		db에 사용자가 입력한 id와 pw가 있는지 확인
		String checked_Id= loginSvc.loginUser(idMac, pwMac);
		
//		checked Id에 데이터가 있을시 세션에 id저장
		if(idMac.equals(checked_Id)) {
			mav.setViewName("redirect:/home");
			session.setAttribute("idMac", idMac);
			mav.addObject("idMac",session.getAttribute("idMac").toString());
		
			return mav;
		} else {
			mav.addObject("msg","잘못된 아이디나 비밀번호 입니다");
		}
		return mav;
	}
	
//	로그아웃메소드
	@GetMapping("/logout")
	public Boolean logout(HttpSession session) {
		log.info("로그아웃");
		session.removeAttribute("idMac");
		return true;
	}
	
//	로그인 확인
	@GetMapping("/loginCheck")
	public ModelAndView loginCheck(HttpSession session, Model model)
	{
		ModelAndView mav = new ModelAndView();

		String userId=(String) session.getAttribute("idMac");

		if(userId==null) {
			mav.addObject("msg", "로그인 후 이용가능합니다");
			mav.addObject(false);
			return mav;
		} else {
			mav.addObject(true);
		}

		return mav;
	}
	
//	아이디 찾기 폼(폼에서 이름,전화번호 기입후 alert창으로 아이디띄움)
	@GetMapping("/findId")
	public String findIdForm() {
		return "/login/findId";
	}
	
//	아이디 찾기 메소드
	@PostMapping("/findId")
	public ModelAndView findId(@RequestParam("nameMac")String nameMac, @RequestParam("emailMac")String emailMac){

		ModelAndView mav = new ModelAndView("/login/findId");
		
		String foundId= loginSvc.findId(nameMac,emailMac);
		
		if(foundId!=null) {
			mav.addObject("foundId",foundId);
		} else {
			mav.addObject("msg","이름이나 이메일이 잘못 입력되었습니다");
		}
		return mav;
	}
	
//	비밀번호 찾기 폼(폼에서 아이디,이름,전화번호 기입후 alert창으로 비밀번호띄움)
	@GetMapping("/findPassword")
	public String findPasswordForm() {
		return "/login/findPassword";
	}
	
//	비밀번호 찾기 메소드
	@PostMapping("/findPassword")
	public ModelAndView findPassword( @RequestParam("idMac")String idMac,
									 @RequestParam("nameMac")String nameMac,
									 @RequestParam("emailMac")String emailMac,
									 MemberDTO memberDTO,
									 HttpSession session) {

		ModelAndView mav = new ModelAndView("/login/findPassword");
		String msg = null;

		try {
			msg = loginSvc.findPassword(idMac,nameMac,emailMac) ? "인증메일 전송 성공" : "인증메일 전송 실패";
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		
		mav.addObject("msg",msg);
		
		return mav;
	}

}
