package com.mac.demo.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mac.demo.model.XY;
import com.mac.demo.service.BigdataService;

@Controller
@RequestMapping("/big")
public class BigdataController {

	@Autowired
	private BigdataService svc;
	
	@GetMapping("/location")
	public String location() {
		return "thymeleaf/mac/bigdata/map";
	}
	
	//해당 데이터 테이블의 구 데이터 가져오기
	@PostMapping("/kind")
	@ResponseBody
	public Map<String,Object> si(@RequestParam("kind")String kind) {
		Map<String, Object> map = new HashMap<>();
		//구 리스트 저장
		List<String> silist = svc.getsi(kind);
		//html 해석하게 수정
		List<String> silist2 = new ArrayList<>();
		for(int i=0; i<silist.size(); i++) {
			String si2 = "<option>"+silist.get(i)+"</option>";
			silist2.add(si2);
		}
		map.put("silist", silist2);
		return map;
	}
	
	//해당 데이터 테이블의 동 데이터 가져오기
	@PostMapping("/dong")
	@ResponseBody
	public Map<String,Object> dong(@RequestParam("si")String si, @RequestParam("kind")String kind) {
		Map<String, Object> map = new HashMap<>();
		List<String> donglist = svc.getdong(si, kind);
		List<String> donglist2 = new ArrayList<>();
		for(int i=0; i<donglist.size(); i++) {
			String dong2 = "<option>"+donglist.get(i)+"</option>";
			donglist2.add(dong2);
		}
		map.put("donglist", donglist2);
		return map;
	}
	
	//해당 데이터 테이블의 골목길 데이터 가져오기
	@PostMapping("/gil")
	@ResponseBody
	public Map<String,Object> gil(@RequestParam("dong")String dong, @RequestParam("kind")String kind) {
		Map<String, Object> map = new HashMap<>();
		List<String> gillist = svc.getgil(dong, kind);
		List<String> gillist2 = new ArrayList<>();
		for(int i=0; i<gillist.size(); i++) {
			String dong2 = "<option>"+gillist.get(i)+"</option>";
			gillist2.add(dong2);
		}
		map.put("gillist", gillist2);
		return map;
	}

	//골목길 좌표 가져오기
	@PostMapping("/xy")
	@ResponseBody
	public Map<String,Object> xy(@RequestParam("gil")String gil) {
		Map<String, Object> map = new HashMap<>();
		XY xy = svc.getxy(gil);
		int x = xy.getX();
		int y = xy.getY();
		map.put("x", x);
		map.put("y", y);
		return map;
	}
}