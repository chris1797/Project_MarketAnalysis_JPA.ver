package com.mac.demo.mappers;

import com.mac.demo.model.Coordinates;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BigdataMapper {

	List<String> getgu(String kind);
	
	List<String> getdong(String gu, String kind);

	List<String> getgil(String dong, String kind);

	Coordinates getxy(String gil);

	List<String> getsvc(String gil, String kind);

	List<String> getyear(String thissvc, String thisgil, String kind);

	List<String> getquarter(String year, String thissvc, String thisgil, String kind);

	List<String> getPopyear(String gil, String kind);

	List<String> getPopquarter(String year, String thisgil, String kind);

	

}