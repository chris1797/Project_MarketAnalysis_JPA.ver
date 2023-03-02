package com.mac.demo.dto;

import com.mac.demo.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Getter
@NoArgsConstructor
public class MemberDTO {

    @NotBlank
    private String member_id;

    @NotBlank
    private String password;

    @NotBlank
    private String nickname;

    @NotBlank(message = "이메일 주소를 입력해주세요.")
    @Email(message = "올바른 이메일 주소를 입력해주세요.")
    private String email;

    private String gender;

    private Date birth;

    @NotBlank(message = "휴대폰 번호를 입력해주세요.")
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대폰 번호를 입력해주세요.")
    private String phonenum;

    private String city;
    private String town;
    private String village;

    private boolean manager;

    private String username;


    public Member toEntity() {
        return Member.builder()
                .member_id(member_id)
                .password(password)
                .nickname(nickname)
                .email(email)
                .gender(gender)
                .birth(birth)
                .phonenum(phonenum)
                .city(city)
                .town(town)
                .village(village)
                .manager(manager)
                .username(username)
                .build();
    }


}
