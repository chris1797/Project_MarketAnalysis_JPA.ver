package com.mac.demo.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mac.demo.model.User;
import com.mac.demo.service.UserService;

@Controller
@RequestMapping("/user")
public class UserController {
	
	//유저 맵퍼
	@Autowired
	private UserService svc;

//	계정추가폼
	@GetMapping("/addForm")
	public String addForm(Model model) {
		User user = new User();
		model.addAttribute("user", user);
		return "thymeleaf/mac/User/addForm";
	}
	
//	계정추가
	@PostMapping("/add")
	@ResponseBody
	public Map<String,Object> add(User user) {
		Map<String, Object> map = new HashMap<>();
		boolean result = svc.add(user);
		map.put("result", result);
		return map;
	}
	
//	계정리스트
	@GetMapping("/list")
	public String list(Model model) {
		List<User> list = svc.getList();
		model.addAttribute("list", list);	
		return "thymeleaf/mac/User/userlist";
	}
	
//	마이페이지
	@GetMapping("/detail")
	public String mypage(@RequestParam("idMac")String idMac, Model model) {
		User user = svc.getOne(idMac);
		model.addAttribute("user", user);
		return "thymeleaf/mac/User/myPage";
	}
	
//	계정 삭제
	@PostMapping("/deleted")
	@ResponseBody
	public Map<String,Object> deleted(User user, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		String idMac = user.getIdMac();
		boolean result = svc.deleted(idMac);
		map.put("result", result);
		return map;
	}
	
//  유저 업데이트폼
	@GetMapping("/updateForm")
	public String update(User user, Model model) {
		System.out.println(user.getIdMac());
		User user2 = svc.getOne(user.getIdMac());
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
	@PostMapping("/idcheck/{idMac}")
	@ResponseBody
	public Map<String, Object> idcheck(@PathVariable("idMac")String idMac) {
		Map<String, Object> map = new HashMap<>();
		boolean result = svc.idcheck(idMac);
		map.put("result", result);
		return map;
	}
	
	
	
}
