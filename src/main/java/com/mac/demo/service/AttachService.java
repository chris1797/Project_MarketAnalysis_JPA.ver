package com.mac.demo.service;

import com.mac.demo.dto.Attach;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.io.UnsupportedEncodingException;
import java.util.List;

public interface AttachService {
    void saveAll(List<Attach> list);
    String getFileName(Long num);
    List<Attach> getFileList(Long pcode);

    ResponseEntity<Resource> download(String contextType, Long fileNum, Resource resource) throws UnsupportedEncodingException;

    List<Attach> findAllByPcode(Long pcode);

    String findFilenameById(Long file_id);

    void deleteById(Long file_id);
}
