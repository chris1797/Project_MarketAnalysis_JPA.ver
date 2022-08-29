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
	
	
	public List<String> getsi(String kind) {
		return dao.getsi(kind);
	}

	public List<String> getdong(String si, String kind) {
		return dao.getdong(si, kind);
	}


	public List<String> getgil(String dong, String kind) {
		return dao.getgil(dong, kind);
	}


	public XY getxy(String gil) {
		return dao.getxy(gil);
	}


}