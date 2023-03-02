package com.mac.demo.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="Member")
public class Member {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String member_id;

    @NotNull
    private String password;

    @NotNull
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

    @Column(columnDefinition = "boolean default false")
    private boolean manager; // 추후 Role로 변경
    private String username;

    @Temporal(TemporalType.DATE)
    private java.util.Date signup_date; // 회원가입일


    @Builder
    public Member(String member_id, String nickname, String password, String email, Date birth, String phonenum,
                     String city, String town, String village) {
        this.member_id = member_id;
        this.nickname = nickname;
        this.email = email;
        this.password = password;
        this.birth = birth;
        this.phonenum = phonenum;
        this.city = city;
        this.town = town;
        this.village = village;
    }

}
