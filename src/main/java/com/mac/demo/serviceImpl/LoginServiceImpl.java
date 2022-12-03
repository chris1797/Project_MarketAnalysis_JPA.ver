package com.mac.demo.serviceImpl;

import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mac.demo.mappers.LoginMapper;


@Service
public class LoginServiceImpl {

	@Autowired
	private LoginMapper dao;
	
	@Autowired
	private JavaMailSender sender;

//	로그인
	public String loginUser(String idMac, String pwMac) {
		
		return dao.loginUser(idMac,pwMac);
	}

//	아이디찾기
	public String findId(String nameMac, String emailMac) {
		
		return dao.findId(nameMac,emailMac);
	}

//	비밀번호찾기/비밀번호찾아서 이메일로 전송
	public boolean findPassword(String idMac, String nameMac, String emailMac) {
		
		String foundPassword= dao.findPassword(idMac,nameMac,emailMac);
		 MimeMessage mimeMessage = sender.createMimeMessage();

	      try {
	         InternetAddress[] addressTo = new InternetAddress[1];
	         addressTo[0] = new InternetAddress(emailMac);
	         

	         mimeMessage.setRecipients(Message.RecipientType.TO, addressTo);

	         mimeMessage.setSubject("창업커뮤니티 비밀번호입니다.");
	         
	         mimeMessage.setContent("비밀번호는 '"+foundPassword+"'입니다.", "text/html;charset=utf-8");
	         
	         sender.send(mimeMessage);
	         return true;
	      } catch (Exception e) {
	    	 
	      }
	      return false;
	}

}
