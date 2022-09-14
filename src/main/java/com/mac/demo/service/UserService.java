package com.mac.demo.service;

import java.util.List;
import java.util.UUID;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mac.demo.mappers.UserMapper;
import com.mac.demo.model.Board;
import com.mac.demo.model.User;


@Service
public class UserService {

	//유저 맵퍼
	@Autowired
	private UserMapper dao;

	@Autowired
	private JavaMailSender sender;
	
	//회원가입
	public boolean add(User user) {
		return dao.add(user) > 0;
	}

	//회원리스트
	public List<User> getList() {
		
		return dao.getList();
	}

	//회원정보
	public User getOne(String idMac) {
		User user = dao.getOne(idMac);
		return user;
	}

	//회원삭제
	public boolean deleted(String idMac) {
		boolean result = dao.deleted(idMac);
		return result;
	}

	//회원정보 수정
	public boolean updated(User user) {
		boolean result = dao.updated(user);
		return result;
	}

	//아이디 체크
	public boolean idcheck(String idMac) {
		User user = dao.getOne(idMac);
		return user == null;
	}

	//이메일 인증
	public String checkmail(String emailMac) {
		UUID randomUUID = UUID.randomUUID();
		
		String random = randomUUID.toString().replaceAll("-", "");
		
		MimeMessage mimeMessage = sender.createMimeMessage();

	      try {
	         InternetAddress[] addressTo = new InternetAddress[1];
	         addressTo[0] = new InternetAddress(emailMac);

	         mimeMessage.setRecipients(Message.RecipientType.TO, addressTo);

	         mimeMessage.setSubject("[골목상권 분석 프로젝트] <이메일 인증 코드 도착!>");
	         mimeMessage.setContent("인증코드입니다. 다음 코드를 입력해 주세요. : "+random ,"text/html;charset=utf-8");
	         
	         sender.send(mimeMessage);
	         return random;
	      } catch (MessagingException e) {
	  
	      }
		return null;
	}

	//닉네임 중복 체크
	public boolean nickCheck(String nick) {
		User user = dao.getOneNick(nick);
		return user == null;
	}

	public List<Board> findWrite(String idMac) {
		System.out.println("111");
		return dao.findWrite(idMac);
		
		
	}
	

	
	
	
	
}
