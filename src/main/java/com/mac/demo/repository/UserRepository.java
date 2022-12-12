package com.mac.demo.repository;

import com.mac.demo.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	User findByUser_id(String id);
	List<User>findByUsernameContaining(String user_name);

//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE USER_TB u SET u.titlemac=?1, b.contentsmac=?2 WHERE u.idmac=?3", nativeQuery = true)
//	int update(String titlemac, String contentsmac, long idmac);
}
