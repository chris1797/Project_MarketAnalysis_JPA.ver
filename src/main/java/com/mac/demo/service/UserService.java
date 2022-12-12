package com.mac.demo.service;

import com.github.pagehelper.PageInfo;
import com.mac.demo.dto.User;

import java.util.List;

public interface UserService {
    List<User> findByUsernameContaining(String userName);
    PageInfo<User> getList();

    boolean delete(Long user_num);
}
