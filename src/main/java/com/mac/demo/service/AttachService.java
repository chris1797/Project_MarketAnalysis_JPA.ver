package com.mac.demo.service;

import com.mac.demo.dto.Attach;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface AttachService {
    String getFileName(Long num);
    List<Attach> getFileList(Long pcode);

    ResponseEntity<Resource> download(String contextType, Long fileNum, Resource resource);
}
