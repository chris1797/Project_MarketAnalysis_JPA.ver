package com.mac.demo.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mac.demo.dto.Attach;
//import com.mac.demo.model.Fileupload;


@Mapper
public interface AttachMapper {

//	int insertUpload(Fileupload vo);
	
	int insertAttach(Attach att);
	
	List<Map<String, Object>> getList();
	
	String getFname(int num);
	
	List<Attach> getFileList(int pcodeMac);
	
	List<Map<String, Object>> getDetailByNum(int num);

	int filedelete(int num);
	
	int insertMultiAttach(List<Attach> list);
	
	int updateMultiAttach(List<Attach> list);

	List<String> getAttachByPnum(int num);

	int deleteAttInfo(int num);

	int deleteUpload(int num);
	
	int getIndex();

}
