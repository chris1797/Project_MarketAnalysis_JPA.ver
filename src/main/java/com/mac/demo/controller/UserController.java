package com.mac.demo.controller;

import com.mac.demo.dto.Board;
import com.mac.demo.dto.User;
import com.mac.demo.service.BoardService;
import com.mac.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.awt.print.Pageable;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
	
	private final UserService userSvc;
	private final BoardService boardSvc;

//	계정추가폼
	@GetMapping("/addForm")
	public ModelAndView addForm() {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/User/addForm");

		mav.addObject("user", new User());

		return mav;
	}
	
//	아이디 체크후 추가폼
	@PostMapping("/addForm/{idMac}")
	public ModelAndView addForm2(@PathVariable("idMac")String idMac) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/User/addForm");

		User user = User.builder()
						.user_id(idMac)
						.build();

		mav.addObject("user", user);

		return mav;
	}
	
//	아이디 체크, 이메일 인증후 추가폼
	@PostMapping("/addForm/{idMac}/{emailMac}")
	public ModelAndView addForm3(@PathVariable("idMac")String idMac,
								 @PathVariable("emailMac")String emailMac,
								 @RequestParam(name ="check", defaultValue="0")int check, Model model) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/home/home");

		User user = User.builder()
						.user_id(idMac)
						.email(emailMac)
						.build();

		if (check == 1) {
			mav.setViewName("thymeleaf/mac/User/addForm");
			mav.addObject("user", user);
			return mav;
		}

		return mav;
	}
	
//	계정추가
	@PostMapping("/add")
	public Map<String,Object> add(User user) {
		Map<String, Object> map = new HashMap<>();
		map.put("result", userSvc.add(user));
		return map;
	}
	
//	계정리스트
	@GetMapping("/list")
	public ModelAndView list() {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/User/userlist");

		mav.addObject("list", userSvc.getList());

		return mav;
	}
	
//	마이페이지
	@GetMapping("/detail/{idMac}")
	public ModelAndView getMyPage(@PathVariable("idMac")String user_id,
							   HttpSession session,
							   @PageableDefault(sort = "wdate", direction = Sort.Direction.DESC) Pageable pageable,
							   @RequestParam(name="page", required = false,defaultValue = "1") int page) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/User/myPage");

		User user = userSvc.getOne(user_id);

		if(!user_id.equals((String)session.getAttribute("idMac"))) {
			mav.setViewName("redirect:/home");
			return mav;
		}

		if((String)session.getAttribute("idMac") == null) {
			mav.setViewName("thymeleaf/mac/home/home");
			return mav;
		}

		mav.addObject("user", user);
		
		Page<Board> boardPage = boardSvc.findByUser_id(pageable);

		mav.addObject("pageInfo", boardPage);

		return mav;

	}
	
//	계정 삭제
	@DeleteMapping("/deleted")
	public Map<String,Object> deleted(com.mac.demo.dto.User user, HttpSession session) {
		Map<String, Object> map = new HashMap<>();
		Long user_idx = user.getUser_num();
		boolean result = userSvc.delete(user_idx);
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
	public ModelAndView update(User user) {

		ModelAndView mav = new ModelAndView("thymeleaf/mac/User/updateForm");

		User user2 = userSvc.getOne(user.getUser_id());
		mav.addObject("user", user2);

		return mav;
	}
	
//  유저 정보 수정
	@PutMapping("/updated")
	public Map<String, Object> edit(User user, HttpSession session) {

		Map<String, Object> map = new HashMap<>();

		boolean result = userSvc.updated(user);
		map.put("result", result);

		return map;
	}
	
//	ID 중복체크
	@PostMapping("/idcheck")
	public Map<String, Object> idcheck(@RequestParam("idMac")String idMac) {

		Map<String, Object> map = new HashMap<>();

		boolean result = userSvc.idcheck(idMac);
		map.put("result", result);
		map.put("id" , idMac);

		return map;
	}
	
//	email 인증 보내기
	@PostMapping("/checkmail")
	public Map<String, Object> emailcheck(@RequestParam("emailMac")String emailMac) {

		Map<String, Object> map = new HashMap<>();
		String random = userSvc.checkmail(emailMac);

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
	public Map<String, Object> checkcode(@RequestParam("code")String code) {

		Map<String, Object> map = new HashMap<>();
		map.put("code", code);

		return map;
	}
	
//	닉네임
	@PostMapping("/nickCheck")
	public Map<String, Object> nickCheck(@RequestParam("nick")String nick) {

		Map<String, Object> map = new HashMap<>();

		boolean result = userSvc.nickCheck(nick);
		map.put("result", result);

		return map;
	}
	
}
