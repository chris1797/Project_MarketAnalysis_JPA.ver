package com.mac.demo.mappers;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.mac.demo.model.Attach;
//import com.mac.demo.model.Fileupload;

@Mapper
public interface AttachMapper {

//	int insertUpload(Fileupload vo);
	
	int insertAttach(Attach att);

	List<Map<String, Object>> getList();
	
	String getFname(int num);

	List<Map<String, Object>> getDetailByNum(int num);

	int remove(int num);
	
	int insertMultiAttach(List<Attach> list);

	List<String> getAttachByPnum(int num);

	int deleteAttInfo(int num);

	int deleteUpload(int num);

}
