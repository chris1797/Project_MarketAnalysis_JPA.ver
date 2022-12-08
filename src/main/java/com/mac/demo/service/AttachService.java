package com.mac.demo.service;

import com.mac.demo.dto.Attach;

import java.util.List;

public interface AttachService {
    String getFileName(Long num);
    List<Attach> getFileList(Long pcode);
}
