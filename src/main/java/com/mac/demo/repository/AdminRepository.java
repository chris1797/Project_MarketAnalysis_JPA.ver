package com.mac.demo.repository;

import com.mac.demo.dto.MemberDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<MemberDTO, Integer> {

}
