package com.mac.demo.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mac.demo.model.User;
import com.mac.demo.service.LoginService;


@RequestMapping("/login")
@Controller
public class LoginController {
	
	@Autowired
	private LoginService svc;
	
//	로그인폼
	@GetMapping("/loginForm")
	public String login() {
		
		return "thymeleaf/mac/login/loginForm";
	}
	
//	세션에 id저장
	@PostMapping("/loginForm")
	public String login(@RequestParam("idMac")String idMac, @RequestParam("pwMac")String pwMac, User user, HttpSession session,Model model){
		
		//db에 사용자가 입력한 id와 pw가 있는지 확인
		System.out.println("아이디"+idMac);
		System.out.println("비밀번호"+pwMac);
//		id,pw가 맞을시 문자열checkedId에 id저장
//		user.setIdMac(idMac);
//		user.setPwMac(pwMac);
		
		String checkedId= svc.loginUser(idMac,pwMac);
		System.out.println("로그인체크드아이디"+checkedId);
		
//		checkdeId에 데이터가 있을시 세션에 id저장
		if(idMac.equals(checkedId)) {
		session.setAttribute("idMac", idMac);
		model.addAttribute("idMac",session.getAttribute("idMac").toString());
		model.addAttribute("msg", session.getAttribute("idMac").toString()+"님 환영합니다");
		//return map;
		
		System.out.println("로그인이 되었습니다");
		System.out.println(session.getAttribute("idMac"));
		return "thymeleaf/mac/home/home";
		}else if(checkedId==null) {
			model.addAttribute("msg","잘못된 아이디나 비밀번호 입니다");
			
			return "thymeleaf/mac/login/loginForm";
		}
		System.out.println("로그인이 실패하였습니다");
		return "thymeleaf/mac/login/loginForm";	
	}
	
//	로그아웃메소드
	@GetMapping("/logout")
	@ResponseBody
	public Map<String,Object> logout(HttpSession session, Model model) 
	{
//		로그인체크확인(실패함 logincheck에서 return으로 loginInputform페이지나 loginInputform url로 넘어가며 기존메소드가끝나지않고)
//		LoginController login = new LoginController();
//		model.addAttribute("msg", "로그인 후 이용가능합니다");
//		login.loginCheck(session,model);
		
//		로그인체크확인2번방식(responsebody인 여기서는 확인불가 board/adsboard에서 계속테스트진행)
//		LoginController login = new LoginController();
//		model.addAttribute("msg", "로그인 후 이용가능합니다");
//		if(login.loginCheck(session,model)!=true) {
//			return "/login/loginForm";
//		}
		
		
		System.out.println(session.getAttribute("idMac"));
		
//		로그아웃
		session.invalidate();
		System.out.println("로그아웃메소드");
		Map<String,Object> map = new HashMap<>();
		map.put("logout", true);
		return map;
	}
	
//	로그인 확인메소드(테스트 결과 실패)
//	@GetMapping("/loginCheck")
//	public String loginCheck(HttpSession session, Model model) 
//	{
//		System.out.println("로그인체크중");
//		String userId=(String) session.getAttribute("idMac");
//		if(userId==null) {
//			model.addAttribute("msg", "로그인 후 이용가능합니다");
//			
//			System.out.println("로그인체크결과 로그인되지않았습니다");
//
//	return "thymeleaf/mac/login/loginForm";
//		}
//		System.out.println("로그인체크결과 로그인되었습니다");
//
//		return null;
//	}
	
//	로그인 확인
	@GetMapping("/loginCheck")
	public boolean loginCheck(HttpSession session, Model model) 
	{
		System.out.println("로그인체크중");
		String userId=(String) session.getAttribute("idMac");
		if(userId==null) {
			model.addAttribute("msg", "로그인 후 이용가능합니다");
			
			System.out.println("로그인체크결과 로그인되지않았습니다");

			return false;
		}
		System.out.println("로그인체크결과 로그인되었습니다");

		return true;
	}
	
//	아이디 찾기 폼(폼에서 이름,전화번호 기입후 alert창으로 아이디띄움)
	@GetMapping("/findId")
	public String findIdForm() {
		
		return "thymeleaf/mac/login/findId";
	}
	
//	아이디 찾기 메소드
	@PostMapping("/findId")
	public String findId(@RequestParam("nameMac")String nameMac, @RequestParam("phoneNumMac")String phoneNumMac, User user, HttpSession session,Model model){
		
		System.out.println("이름"+nameMac);
		System.out.println("전화번호"+phoneNumMac);
		
		String foundId= svc.findId(nameMac,phoneNumMac);
		System.out.println("찾은 아이디"+foundId);
		
//		foundId에 데이터가 있을시 alert창으로 찾은 아이디 알려줌
		if(foundId!=null){
		model.addAttribute("msg", nameMac+"님 아이디는 ("+foundId+") 입니다");
		//return map;
		
		return "thymeleaf/mac/login/loginForm";
		}else{
			model.addAttribute("msg","이름이나 이메일이 잘못 입력되었습니다");
			
			return "thymeleaf/mac/home/home";
		}
	}
	
//	비밀번호 찾기 폼(폼에서 아이디,이름,전화번호 기입후 alert창으로 비밀번호띄움)
	@GetMapping("/findPassword")
	public String findPasswordForm() {
		
		return "thymeleaf/mac/login/findPassword";
	}
	
//	비밀번호 찾기 메소드
	@PostMapping("/findPassword")
	public String findPassword(@RequestParam("idMac")String idMac,@RequestParam("nameMac")String nameMac, @RequestParam("phoneNumMac")String phoneNumMac, User user, HttpSession session,Model model){
		
		System.out.println("이름"+idMac);
		System.out.println("이름"+nameMac);
		System.out.println("전화번호"+phoneNumMac);
		
		String foundPassword= svc.findPassword(idMac,nameMac,phoneNumMac);
		System.out.println("찾은 비밀번호"+foundPassword);
		
//		foundId에 데이터가 있을시 alert창으로 찾은 아이디 알려줌
		if(foundPassword!=null){
		model.addAttribute("msg", idMac+"님 비밀번호는 ("+foundPassword+") 입니다");
		//return map;
		
		return "thymeleaf/mac/login/loginForm";
		}else{
			model.addAttribute("msg","아이디나 이름이나 이메일이 잘못 입력되었습니다");
			
			return "thymeleaf/mac/home/home";
		}
	}
}
