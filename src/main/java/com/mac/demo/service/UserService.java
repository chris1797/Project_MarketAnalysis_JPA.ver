package com.mac.demo.service;

import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.MemberDTO;

import java.util.List;

public interface UserService {
    List<MemberDTO> findByUsernameContaining(String userName);
    PageInfo<MemberDTO> getList();

    boolean delete(Long user_num);

    boolean add(MemberDTO memberDTO);

    // Impl 수정 필요

    MemberDTO getOne(String user_id);

    boolean updated(MemberDTO memberDTO);

    boolean idcheck(String idMac);

    boolean nickCheck(String nick);

    String checkmail(String emailMac);
}
