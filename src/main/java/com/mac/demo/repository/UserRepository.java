package com.mac.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mac.demo.model.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByIdmac(String idmac);

	@Transactional
	@Modifying
	@Query("UPDATE USER_TB u SET u.titlemac=?1, b.contentsmac=?2 WHERE u.idmac=?3")
	int update(String titlemac, String contentsmac, long idmac);
}
