package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.FileMedia;
import org.springframework.web.multipart.MultipartFile;

public interface FileMediaService {
    FileMedia uploadFile(MultipartFile file);
    FileMedia getFileById(Long fileId);
}