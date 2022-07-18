package com.ezen.demo.service;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ezen.demo.mappers.FileuploadMapper;
import com.ezen.demo.model.AttachVO;
import com.ezen.demo.model.Fileupload;

@Service
public class FileuploadService 
{
	@Autowired
	private FileuploadMapper dao;

	public boolean insert(Fileupload vo) {
		int pnum = 0;
		if(vo.getWriter() != null) {
			dao.insertUpload(vo);    //upload_tb에 저장
			pnum = vo.getNum();  // 자동 증가된 업로드 번호를 받음
			System.out.println("vo.getNum = " + pnum);
		}
		
		List<AttachVO> attList = vo.getAttach();
		
		if(pnum>0) {
			for(int i=0; i<attList.size(); i++) {
				attList.get(i).setPnum(pnum);;
			}
		}
		int res = dao.insertMultiAttach(attList);
		/*
		int insertedCnt = 0;
		for(int i=0; i<attList.size(); i++) {
			insertedCnt += dao.insertAttach(attList.get(i));
		}
		*/
		return res==attList.size();
	}
	
	public List<Fileupload> getList() {
		List<Map<String,Object>> list = dao.getList();
		List<Fileupload> volist = new ArrayList<>();
		
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> map = list.get(i);
			
			String fname = (String) map.get("FNAME");
			
			AttachVO attvo = null;
			
			if(fname != null) {
				attvo = new AttachVO();
				int fnum = ((BigDecimal)map.get("FNUM")).intValue();
				attvo.setNum(fnum);
				attvo.setFname(fname);
			}
			
			Fileupload vo = new Fileupload();
			int num = ((BigDecimal)map.get("NUM")).intValue();
			vo.setNum(num);
			
			if(volist.contains(vo)) {
				volist.get(volist.size()-1).getAttach().add(attvo);
			} else {
				String writer = (String) map.get("WRITER");
				Date udate = new Date(((Timestamp)map.get("UDATE")).getTime());
				String comments = (String) map.get("COMMENTS");
				
				vo.setWriter(writer);
				vo.setUdate(udate);
				vo.setComments(comments);
				vo.getAttach().add(attvo);
				
				volist.add(vo);
			}
		}
		return volist;
	}

	public String getFname(int num) {
		String fname = dao.getFname(num);
		return fname;
	}

	public Fileupload getDetailByNum(int _num) {
		List<Map<String,Object>> list = dao.getDetailByNum(_num);
		
		Fileupload vo = new Fileupload();
		for(int i=0; i<list.size(); i++) {
			Map<String, Object> map = list.get(i);
			
			int num = ((BigDecimal)map.get("NUM")).intValue();
			String writer = (String)map.get("WRITER");
			Date udate = new Date(((Timestamp)map.get("UDATE")).getTime());
			String comments = (String)map.get("COMMENTS");
			
			vo.setNum(num);
			vo.setWriter(writer);
			vo.setUdate(udate);
			vo.setComments(comments);
			
			String fname = (String)map.get("FNAME");
			
			if(fname != null) {
				AttachVO attvo = new AttachVO();
				int fnum = ((BigDecimal)map.get("FNUM")).intValue();
				attvo.setNum(fnum);
				attvo.setFname(fname);
				vo.getAttach().add(attvo);
			}
		}
		return vo;
	}

	public boolean remove(int num) {
		int removed = dao.remove(num);
		return removed > 0;
	}

	@Transactional(rollbackFor = {Exception.class})
	public boolean delete3(HttpServletRequest request, int num) throws Exception {
		boolean attDeleted = dao.deleteAttInfo(num)>0;
		if(!attDeleted) throw new Exception("attach_tb rows delete fail");
		
		boolean uploadDeleted = dao.deleteUpload(num)>0;
		if(!uploadDeleted) throw new Exception("upload_tb rows delete fail");
		
		//게시물 번호를 이용해서 첨부파일명 모두 가져오기
		List<String> fnameList = dao.getAttachByPnum(num);
		String dir = request.getServletContext().getRealPath("WEB-INF/files/");
		int delCnt = 0;
		
			if(!(fnameList==null) || fnameList.size()==0) {
				for(int i=0; i<fnameList.size(); i++) {
					String path = dir + fnameList.get(i);
					File f = new File(path);
					if(!f.exists()) {
						System.out.println("File Not Found, '" + path + "'");
						continue;
					}
					delCnt += f.delete() ? 1:0;
				}
				if(delCnt==fnameList.size()) {
					System.out.println("Successfully deleted!");
				} else {
					System.out.println("Faile to delete the files");
					throw new Exception("file delete fail");
				}
			}
		return true;
	}
	
	/*
	public boolean insertMultiAttach(Fileupload vo) {
		int pnum = vo.getNum();  // 자동 증가된 업로드 번호를 받음
		
		List<AttachVO> attList = vo.getAttach();

		int totalSuccess = 0;
		for(int i=0;i<attList.size();i++)
		{
			Map<String,Object> fmap = new HashMap<>();
			fmap.put("pnum", Integer.valueOf(pnum));
			fmap.put("fname", attList.get(i).getFname());
			fmap.put("fpath", vo.getFpath());
			totalSuccess += dao.insertAttach(fmap);   // 첨부파일 정보 저장
		}
		return totalSuccess==attList.size();
	}
	*/


}