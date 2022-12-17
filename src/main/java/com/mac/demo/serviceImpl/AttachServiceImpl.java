package com.mac.demo.serviceImpl;

import com.mac.demo.dto.Attach;
import com.mac.demo.dto.Board;
import com.mac.demo.repository.AttachRepository;
import com.mac.demo.service.AttachService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttachServiceImpl implements AttachService {


    private final AttachRepository attachRepository;



    @Override
    public List<Attach> findAllByPcode(Long pcode) {
        return attachRepository.findAllByPcode(pcode);
    }

    @Override
    public String findFilenameById(Long file_id) {
        return attachRepository.findFilenameByAtt_num(file_id);
    }

    @Override
    public void deleteById(Long file_id) {

    }

    @Override
    public void saveAll(List<Attach> list) {

        try {
            attachRepository.saveAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFileName(Long fileIdx) {
        return attachRepository.findFilenameByAtt_num(fileIdx);
    }


    @Override
    public List<Attach> getFileList(Long pcode) {
        return attachRepository.findAllByPcode(pcode);
    }


    public List<Attach> getFileSet(Board board, MultipartFile[] mfiles, String savePath) {
        String fname_changed = null;
        List<Attach> attList = new ArrayList<>();

        try {
            for (int i = 0; i < mfiles.length; i++) {
                String[] token = mfiles[i].getOriginalFilename().split("\\.");
                fname_changed = token[0] + "_" + System.nanoTime() + "." + token[1];

                Attach _att = Attach.builder()
                                    .pcode(board.getBoard_num())
                                    .user_id(board.getUser_id())
                                    .filename(fname_changed)
                                    .filepath(savePath)
                                    .build();

                attList.add(_att);
                /**
                 * 메모리에 있는 파일을 저장경로에 옮김, 로컬 경로에 있는 파일만 선택 가능
                 * 추후 AWS S3로 전환
                 */
                mfiles[i].transferTo(
                        new File(savePath + "/" + fname_changed));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return attList;
    }

    //	파일 다운로드
    public ResponseEntity<Resource> download (String contentType, Long FileNum, Resource resource) throws UnsupportedEncodingException {

        if (contentType == null) contentType = "application/octet-stream";

        ResponseEntity<Resource> file =  ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + new String(resource.getFilename().getBytes("UTF-8"), "ISO-8859-1") + "\"")

                // .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                // HttpHeaders.CONTENT_DISPOSITION는 http header를 조작하는 것, 화면에 띄우지 않고 첨부화면으로
                // 넘어가게끔한다
                // filename=\"" + resource.getFilename() + "\"" 는 http프로토콜의 문자열을 고대로 쓴 것
                .body(resource);

        return file;
    }


}
