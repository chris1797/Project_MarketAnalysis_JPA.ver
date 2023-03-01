package com.mac.demo.repository;

import com.mac.demo.dto.AttachDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachRepository extends JpaRepository<AttachDTO, Long> {

	List<AttachDTO> findAllByPcode(Long pcode);

	/**
	 * 파일 idx로 파일이름 가져오기
	 * @param num
	 * @return
	 */
	String findFilenameByAtt_num(Long num);

}
