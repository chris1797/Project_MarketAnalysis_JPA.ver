package com.mac.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/stores")
@Controller
public class StoresController {

	@Autowired
	private StoresMapper dao;
}
