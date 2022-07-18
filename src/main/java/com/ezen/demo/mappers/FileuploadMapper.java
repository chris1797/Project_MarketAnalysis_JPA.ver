package com.ezen.demo.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ezen.demo.model.AttachVO;
import com.ezen.demo.model.Fileupload;

@Mapper
public interface FileuploadMapper {

	int insertUpload(Fileupload vo);
	
	int insertAttach(AttachVO att);

	List<Map<String, Object>> getList();
	
	String getFname(int num);

	List<Map<String, Object>> getDetailByNum(int num);

	int remove(int num);
	
	int insertMultiAttach(List<AttachVO> list);

	List<String> getAttachByPnum(int num);

	int deleteAttInfo(int num);

	int deleteUpload(int num);

}