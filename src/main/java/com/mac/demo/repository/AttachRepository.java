package com.mac.demo.repository;

import com.mac.demo.dto.Attach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachRepository extends JpaRepository<Attach, Long> {

	List<Attach> findAllByPcodemac(Long pcodeMac);
	

}
