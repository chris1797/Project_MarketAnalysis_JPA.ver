package com.mac.demo.dto;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.Date;

@Entity
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name="USER_TB")
public class User {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_num;

    @NotBlank
    private String user_id;

    @NotBlank
    private String user_pw;
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
    private boolean manager;
    private String username;

    @Temporal(TemporalType.DATE)
    private java.util.Date signup_date; // 회원가입일


    @Builder
    public User(String user_id, String nickname, String user_pw, String email, Date birth, String phonenum,
                     String city, String town, String village) {
        this.user_id = user_id;
        this.nickname = nickname;
        this.email = email;
        this.user_pw = user_pw;
        this.birth = birth;
        this.phonenum = phonenum;
        this.city = city;
        this.town = town;
        this.village = village;
    }

    public com.mac.demo.dto.User toEntity() {
        return com.mac.demo.dto.User.builder()
                .user_pw(user_pw)
                .birth((java.sql.Date) birth)
                .phonenum(phonenum)
                .city(city)
                .town(town)
                .village(village)
                .build();
    }
}
