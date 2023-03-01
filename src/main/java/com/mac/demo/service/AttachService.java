package com.mac.demo.service;

import com.mac.demo.dto.AttachDTO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AttachService {
    void saveAll(List<AttachDTO> list);
    String getFileName(Long num);
    List<AttachDTO> getFileList(Long pcode);

    ResponseEntity<Resource> download(String contextType, Long fileNum, Resource resource) throws UnsupportedEncodingException;

    List<AttachDTO> findAllByPcode(Long pcode);

    String findFilenameById(Long file_id);

    void deleteById(Long file_id);
}
