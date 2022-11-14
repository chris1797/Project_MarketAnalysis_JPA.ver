package com.mac.demo.repository;

import com.mac.demo.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<User, Integer> {

}
