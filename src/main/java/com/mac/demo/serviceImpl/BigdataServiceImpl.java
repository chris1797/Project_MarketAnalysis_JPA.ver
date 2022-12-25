package com.mac.demo.serviceImpl;

import com.mac.demo.model.XY;
import com.mac.demo.service.BigdataService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class BigdataServiceImpl implements BigdataService {

	@Autowired
	private BigdataMapper dao;

	private final BigdataRepository bigdataRepository;
	
	@Override
	public List<String> getgu(String kind) {
		return dao.getgu(kind);
	}

	@Override
	public List<String> getdong(String gu, String kind) {
		return dao.getdong(gu, kind);
	}

	@Override
	public List<String> getgil(String dong, String kind) {
		return dao.getgil(dong, kind);
	}

	@Override
	public XY getxy(String gil) {
		return dao.getxy(gil);
	}

	@Override
	public List<String> getsvc(String gil, String kind) {
		return dao.getsvc(gil, kind);
	}

	@Override
	public List<String> getyear(String thissvc, String thisgil, String kind) {
		return dao.getyear(thissvc, thisgil, kind);
	}

	@Override
	public List<String> getquarter(String year, String thissvc, String thisgil, String kind) {
		return dao.getquarter(year, thissvc, thisgil, kind);
	}

	@Override
	public List<String> getPopyear(String gil, String kind) {
		return dao.getPopyear(gil, kind);
	}

	@Override
	public List<String> getquarter(String year, String thisgil, String kind) {
		return dao.getPopquarter(year, thisgil, kind);
	}
}