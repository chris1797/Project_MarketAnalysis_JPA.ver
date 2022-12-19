package com.mac.demo.controller;


import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@RequestMapping
public class CustomErrorController implements ErrorController {

	@RequestMapping("/error")
	public ModelAndView error(HttpServletRequest request, Model model) {
		ModelAndView mav = new ModelAndView();

		Object status =request.getAttribute("javax.servlet.error.status_code");
		
		mav.addObject("status", "상태:" +status);
		mav.addObject("path", request.getAttribute("javax.servlet.error.request_uri"));
		mav.addObject("timestamp", new Date());
		
		Object exceptionObj =request.getAttribute("javax.servlet.error.exception");

		if(exceptionObj != null) {
			Throwable e =((Exception)exceptionObj).getCause();
			mav.addObject("exception",e.getClass().getName());
			mav.addObject("message", e.getMessage());
		}
		
		if(status.equals(HttpStatus.NOT_FOUND.value())) {
			mav.setViewName("thymeleaf/mac/error/404error");
			return mav;
		}else if(status.equals(405)) {
			mav.setViewName("thymeleaf/mac/error/405error");
			return mav;
		}else {
			mav.setViewName("thymeleaf/mac/error/500error");
			return mav;
		}
	}
}
