package com.mac.demo.repository;

import com.mac.demo.dto.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String>{

	User findByIdmac(String idmac);

//	@Transactional
//	@Modifying
//	@Query(value = "UPDATE USER_TB u SET u.titlemac=?1, b.contentsmac=?2 WHERE u.idmac=?3", nativeQuery = true)
//	int update(String titlemac, String contentsmac, long idmac);
}
