package com.mac.demo.serviceImpl;

import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.MemberDTO;
import com.mac.demo.repository.UserRepository;
import com.mac.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final JavaMailSender sender;


	//회원가입
	public boolean add(MemberDTO memberDTO) {
		userRepository.save(memberDTO);
		return true;
	}

	//회원리스트
	public PageInfo<MemberDTO> getList() {
		PageInfo<MemberDTO> pageInfo = new PageInfo<>(userRepository.findAll());
		return pageInfo;
	}

	//회원정보
	public MemberDTO getOne(String user_id) {
		return userRepository.findByUser_id(user_id);
	}

	//회원삭제
	@Override
	public boolean delete(Long user_num) {
		try {
			userRepository.deleteById(user_num);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	//회원정보 수정
	public boolean updated(MemberDTO memberDTO) {
		try {
//			userRepository.update(user.getPwmac(), user.getEmailmac());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Transactional
	public boolean update(String id) {
		return true;
	}

	//아이디 체크
	public boolean idcheck(String user_id) {
		try {
			MemberDTO memberDTO = userRepository.findByUser_id(user_id);
			if(memberDTO == null) return false;
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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
			  e.printStackTrace();
	      }

		return null;
	}

	/**
	 * 해당 닉네임에 해당하는 유저 존재 여부 체크
	 * @param nickName
	 * @return boolean
	 */
	public boolean nickCheck(String nickName) {

		try {
			MemberDTO memberDTO = userRepository.findByNickname(nickName);
			if (memberDTO != null) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
    public List<MemberDTO> findByUsernameContaining(String userName) {
		return userRepository.findByUsernameContaining(userName);
    }
}
