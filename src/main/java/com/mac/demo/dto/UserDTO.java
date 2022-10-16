package com.mac.demo.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserDTO {
    private long nummac;
    private String idmac;
    private String pwmac;
    private String nicknamemac;
    private String emailmac;
    private String gendermac;
    private java.util.Date birthmac;
    private String phonenummac;
    private String citymac;
    private String townmac;
    private String villagemac;
    private int managermac;
    private String namemac;
    private java.util.Date datemac;

    @Builder
    public UserDTO(String idmac, String pwmac, String nicknamemac,
                    String emailmac, java.util.Date birthmac,
                   String phonenummac, String citymac, String townmac, String villagemac,
                   String namemac) {
        this.idmac = idmac;
        this.pwmac = pwmac;
        this.nicknamemac = nicknamemac;
        this.emailmac = emailmac;
        this.birthmac = birthmac;
        this.phonenummac = phonenummac;
        this.citymac = citymac;
        this.townmac = townmac;
        this.villagemac = villagemac;
        this.namemac = namemac;
    }

}
