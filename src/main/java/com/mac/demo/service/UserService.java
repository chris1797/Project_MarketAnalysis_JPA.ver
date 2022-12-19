package com.mac.demo.service;

import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.Board;
import com.mac.demo.dto.User;

import java.util.List;

public interface UserService {
    List<User> findByUsernameContaining(String userName);
    PageInfo<User> getList();

    boolean delete(Long user_num);

    boolean add(User user);

    // Impl 수정 필요
    List<Board> findWrite(String user_id);

    User getOne(String user_id);

    boolean updated(User user);

    boolean idcheck(String idMac);

    boolean nickCheck(String nick);

    String checkmail(String emailMac);
}
