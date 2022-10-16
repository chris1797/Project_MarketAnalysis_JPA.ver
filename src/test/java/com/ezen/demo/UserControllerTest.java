package com.ezen.demo;

import com.mac.demo.model.User;
import com.mac.demo.repository.UserRepository;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// host가 사용하지 않는 랜덤 포트를 사용하겠다는 의미
public class UserControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @After
    public void end() throws Exception {
        userRepository.deleteAll();
    }

    @Test
    public void update() throws Exception {
        String idmac = "user1";
        User saveUser = userRepository.save(User.builder()
                .idmac(idmac)
                .pwmac("skblue1797!")
                .phonenummac("010-1234-1234")
                .build());
    }
}
