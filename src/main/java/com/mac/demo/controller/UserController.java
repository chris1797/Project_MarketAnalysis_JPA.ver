package com.mac.demo.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.mac.demo.model.Board;
import com.mac.demo.model.User;
import com.mac.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//유저 맵퍼
	@Autowired
	private UserService svc;

//	계정추가폼
	@GetMapping("/addForm")
	public String addForm(Model model) {
		model.addAttribute("user", new User());
		return "thymeleaf/mac/User/addForm";
	}
	
//	아이디 체크후 추가폼
	@PostMapping("/addForm/{idMac}")
	public String addForm2(@PathVariable("idMac")String idMac, Model model) {
		User user = new User();
		user.setIdmac(idMac);
		model.addAttribute("user", user);
		return "thymeleaf/mac/User/addForm";
	}
	
//	아이디 체크, 이메일 인증후 추가폼
	@PostMapping("/addForm/{idMac}/{emailMac}")
	public String addForm3(@PathVariable("idMac")String idMac, 
			@PathVariable("emailMac")String emailMac, 
			@RequestParam(name ="check", defaultValue="0")String check, Model model) {
		User user = new User();
		user.setIdmac(idMac);
		user.setEmailmac(emailMac);
		if(Integer.parseInt(check) == 1) {
			model.addAttribute("user", user);
			return "thymeleaf/mac/User/addForm";
		}
		return "thymeleaf/mac/home/home";
	}
	
//	계정추가
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> add(User user) {
		Map<String, Object> map = new HashMap<>();
		map.put("result", svc.add(user));
		return map;
	}
	
//	계정리스트
	@GetMapping("/list")
	public String list(Model model) {
		model.addAttribute("list", svc.getList());
		return "thymeleaf/mac/User/userlist";
	}
	
//	마이페이지
	@GetMapping("/detail/{idMac}")
	public String mypage(@PathVariable("idMac")String idMac, Model model, HttpSession session,@RequestParam(name="page", required = false,defaultValue = "1") int page) {
		User user = svc.getOne(idMac);
	
		
		if(idMac.equals((String)session.getAttribute("idMac"))==false) {
			return "redirect:/home";
		};
	
		
		if((String)session.getAttribute("idMac") == null){ //세션을 가져옴
			return "thymeleaf/mac/home/home";
		};
		
		
		model.addAttribute("user", user);
		
		PageHelper.startPage(page, 5);
		PageInfo<Board> pageInfo = new PageInfo<>(svc.findWrite(idMac));
		System.out.println(svc.findWrite(idMac).toString());
		model.addAttribute("pageInfo", pageInfo);
		model.addAttribute("page", page);
		
		return "thymeleaf/mac/User/myPage";

	}
	
//	계정 삭제
	@PostMapping("/deleted")
	@ResponseBody
	public Map<String,Object> deleted(User user, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String idMac = user.getIdmac();
		boolean result = svc.deleted(idMac);
		map.put("result", result);
		
		if((String)session.getAttribute("idMac") == null){ //세션을 가져옴
			result = false;
			map.put("result", result);
			return map;
		};
		if(result==true) {
			session.removeAttribute("idMac");
		};
		
		return map;
	}
	
//  유저 업데이트폼
	@GetMapping("/updateForm")
	public String update(User user, Model model) {
//		System.out.println("여기누");
		User user2 = svc.getOne(user.getIdmac());
		model.addAttribute("user", user2);
		return "thymeleaf/mac/User/updateForm";
	}
	
//  유저 정보 수정
	@PostMapping("/updated")
	@ResponseBody
	public Map<String, Object> edit(User user, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		boolean result = svc.updated(user);
		map.put("result", result);
		return map;
	}
	
//	ID 중복체크
	@PostMapping("/idcheck")
	@ResponseBody
	public Map<String, Object> idcheck(@RequestParam("idMac")String idMac) {
		Map<String, Object> map = new HashMap<>();
		boolean result = svc.idcheck(idMac);
		map.put("result", result);
		map.put("id" , idMac);
		return map;
	}
	
//	email 인증 보내기
	@PostMapping("/checkmail")
	@ResponseBody
	public Map<String, Object> emailcheck(@RequestParam("emailMac")String emailMac) {
		Map<String, Object> map = new HashMap<>();
		String random = svc.checkmail(emailMac);
		if(random!=null) {
			map.put("result", true);
		} else {
			map.put("result", false);
		}
		map.put("code", random);
		map.put("emailMac", emailMac);
		return map;
	}
	
//	이메일 인증코드
	@PostMapping("/checkcode")
	@ResponseBody
	public Map<String, Object> checkcode(@RequestParam("code")String code) {
		Map<String, Object> map = new HashMap<>();
		map.put("code", code);
		return map;
	}
	
//	닉네임
	@PostMapping("/nickCheck")
	@ResponseBody
	public Map<String, Object> nickCheck(@RequestParam("nick")String nick) {
		Map<String, Object> map = new HashMap<>();
		boolean result = svc.nickCheck(nick);
		map.put("result", result);
		return map;
	}
	
}
