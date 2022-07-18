package com.ezen.demo.controller;

import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ezen.demo.mappers.FileuploadMapper;
import com.ezen.demo.model.AttachVO;
import com.ezen.demo.model.Fileupload;
import com.ezen.demo.service.FileuploadService;
import oracle.jdbc.driver.json.tree.JsonpPrimitive.JsonpNumberImpl;

@Controller
@RequestMapping("/filesupload")
public class FileuploadController {
	
	@Autowired
	ResourceLoader resourceLoader;
	
	
	@Autowired
	private FileuploadService svc;
	
	@GetMapping("/upload")
	public String getForm() {
		return "fileupload/fileupload_form";
	}
	
	@PostMapping("/upload")
	@ResponseBody
	public String upload(@RequestParam("files") MultipartFile[] mfiles,
						@RequestParam(name = "pnum", defaultValue = "0") int pnum, 
						HttpServletRequest request, 
						Fileupload vo) {
		
		System.out.println("pnum = " + pnum);
		System.out.println("writer = " + vo.getWriter());
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");

		vo.setFpath(savePath);

		List<AttachVO> attList = new ArrayList<>();
		try {
			// 업로드

			for (int i = 0; i < mfiles.length; i++) {
				// mfiles 파일명 수정
				String[] token = mfiles[i].getOriginalFilename().split("\\.");
				String fname_changed = token[0] + "_"+ System.nanoTime() + "." + token[1];
				
				//attach 객체 만들어서 가공
				AttachVO att = new AttachVO();
				att.setPnum(pnum);
				att.setFname(fname_changed);
				att.setFpath(savePath);
				
				attList.add(att);

				mfiles[i].transferTo( // 메모리에 있는 파일을 저장경로에 옮기는 method, local 디렉토리에 있는 그 파일만 셀렉가능
						new File(savePath + "/" + fname_changed));
			}
			vo.setAttach(attList);
			boolean inserted = svc.insert(vo);
//			String msg = String.format("파일(%d)개 저장성공(작성자:%s)", mfiles.length, vo.getWriter());
			return "{\"inserted\":" + inserted +"}";
		} catch (Exception e) {
			e.printStackTrace();
			return "{\"inserted\":" + false +"}";
		}
	}
	
	@GetMapping("/list")
	public String getList(Model model) {
		List<Fileupload> list = svc.getList();
		model.addAttribute("list", list);
		return "fileupload/fileupload_list";
	}
	
	@GetMapping("/download/{num}")
	public ResponseEntity<Resource> download(HttpServletRequest request, @PathVariable int num) {
		String filename = svc.getFname(num);
		Resource resource = resourceLoader.getResource("WEB-INF/files/" + filename);
		System.out.println("파일명:" + resource.getFilename());
		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			//System.out.println(contentType); // return : image/jpeg
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				// HttpHeaders.CONTENT_DISPOSITION는 http header를 조작하는 것, 화면에 띄우지 않고 첨부화면으로
				// 넘어가게끔한다
				// filename=\"" + resource.getFilename() + "\"" 는 http프로토콜의 문자열을 고대로 쓴 것
				.body(resource);
	}
	
	@GetMapping("/detail/{num}")
	public String getDetail(Model model,
							@PathVariable int num) {
		Fileupload vo = svc.getDetailByNum(num);
		model.addAttribute("vo", vo);
		return "fileupload/fileupload_detail";
	}
	
	@PostMapping("/remove/{num}")
	@ResponseBody
	public Map<String, Object> remove(@PathVariable("num") int num){
		Map<String,Object> map = new HashMap<String, Object>();
		
		String filename = svc.getFname(num);
		Resource resource = resourceLoader.getResource("WEB-INF/files/" + filename);
		//sysout(resource) return : ServletContext resource [/WEB-INF/files/JSP & Servlet.JPG]
		try {
			File file = new File(resource.getFile().getAbsolutePath() +File.separator);
			System.out.println(file);
			boolean removed = file.delete();
			System.out.println("file delete check : " + removed);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		boolean removed = svc.remove(num);
		map.put("removed", removed);
		return map;
	}
	
	@GetMapping("/delete3/{num}")
	@ResponseBody
	public Map<String, Boolean> delete(HttpServletRequest request, @PathVariable("num") int num) throws Exception {
		boolean deleted = svc.delete3(request, num);
		System.out.println(deleted);
		Map<String, Boolean> map = new HashMap<>();
		map.put("deleted", deleted);
		return map;
	}
	
	@PostMapping("/update")
	@ResponseBody
	public String update(@RequestParam("files") MultipartFile[] mfiles, HttpServletRequest request,
			@RequestParam(name="pnum", defaultValue = "0") int pnum,
			Fileupload vo) {
		
		System.out.println("pnum = " + pnum);
		ServletContext context = request.getServletContext();
		String savePath = context.getRealPath("/WEB-INF/files");

		vo.setFpath(savePath);

		List<AttachVO> attList = new ArrayList<>();
		
		boolean updated = false;
		try {

			for (int i = 0; i < mfiles.length; i++) {
				String[] token = mfiles[i].getOriginalFilename().split("\\.");
				String fname_changed = token[0] + System.nanoTime() + "." + token[1];
				
				AttachVO att = new AttachVO();
				att.setPnum(pnum);
				att.setFpath(savePath);
				att.setFname(fname_changed);
				attList.add(att);

				mfiles[i].transferTo( // 메모리에 있는 파일을 저장경로에 옮기는 method, local 디렉토리에 있는 그 파일만 셀렉가능
						new File(savePath + "/" + fname_changed));
			}
			vo.setNum(pnum);
			vo.setAttach(attList);
			updated = svc.insert(vo);
		} catch (Exception e) {
			e.printStackTrace();
			updated = false;
		}
		System.out.println(updated);
		return "{\"updated\":" + updated + "}";
		
	}
	
}