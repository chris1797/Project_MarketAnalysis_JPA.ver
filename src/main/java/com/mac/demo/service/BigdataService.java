package com.mac.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mac.demo.mappers.BigdataMapper;
import com.mac.demo.model.XY;

@Service
public class BigdataService {

	@Autowired
	private BigdataMapper dao;
	
	
	public List<String> getgu(String kind) {
		return dao.getgu(kind);
	}

	public List<String> getdong(String gu, String kind) {
		return dao.getdong(gu, kind);
	}

	public List<String> getgil(String dong, String kind) {
		return dao.getgil(dong, kind);
	}

	public XY getxy(String gil) {
		return dao.getxy(gil);
	}

	public List<String> getsvc(String gil, String kind) {
		return dao.getsvc(gil, kind);
	}

	public List<String> getyear(String thissvc, String thisgil, String kind) {
		return dao.getyear(thissvc, thisgil, kind);
	}

	public List<String> getquarter(String year, String thissvc, String thisgil, String kind) {
		return dao.getquarter(year, thissvc, thisgil, kind);
	}

	public List<String> getPopyear(String gil, String kind) {
		return dao.getPopyear(gil, kind);
	}

	public List<String> getquarter(String year, String thisgil, String kind) {
		return dao.getPopquarter(year, thisgil, kind);
	}
}