package com.mac.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.mac.demo.model.User;

@Repository
public interface UserRepositroy extends JpaRepository<User, Integer>{

	User findByIdmac(String idmac);
}
