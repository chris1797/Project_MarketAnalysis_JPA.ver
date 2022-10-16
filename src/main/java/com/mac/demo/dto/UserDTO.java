package com.mac.demo.dto;


import com.mac.demo.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Date;

@Getter
@NoArgsConstructor
public class UserDTO {
    private long nummac;
    private String idmac;
    private String pwmac;
    private String nicknamemac;
    private String emailmac;
    private String gendermac;
    private Date birthmac;
    private String phonenummac;
    private String citymac;
    private String townmac;
    private String villagemac;
    private int managermac;
    private String namemac;
    private java.util.Date datemac;

    @Builder
    public UserDTO(String pwmac, String emailmac, Date birthmac, String phonenummac,
                   String citymac, String townmac, String villagemac
                   ) {
        this.pwmac = pwmac;
        this.birthmac = birthmac;
        this.phonenummac = phonenummac;
        this.citymac = citymac;
        this.townmac = townmac;
        this.villagemac = villagemac;
    }

    public User toEntity() {
        return User.builder()
                .pwmac(pwmac)
                .birthmac((java.sql.Date) birthmac)
                .phonenummac(phonenummac)
                .citymac(citymac)
                .townmac(townmac)
                .villagemac(villagemac)
                .build();
    }

}
