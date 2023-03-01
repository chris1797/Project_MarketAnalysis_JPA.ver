package com.ezen.demo;

import com.mac.demo.dto.MemberDTO;
import com.mac.demo.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
// host가 사용하지 않는 랜덤 포트를 사용하겠다는 의미
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @After
    public void end() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void savetest() throws Exception {
        String id = "user1";
        MemberDTO memberDTO = MemberDTO.builder()
                .user_id(id)
                .user_pw("123")
                .phonenum("010-1234-1234")
                .build();

        userRepository.save(memberDTO);
    }
}
