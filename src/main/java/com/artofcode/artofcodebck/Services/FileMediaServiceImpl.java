package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.FileMedia;
import com.artofcode.artofcodebck.Repositories.FileMediaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class FileMediaServiceImpl implements FileMediaService {

    @Autowired
    private FileMediaRepository fileMediaRepository;

    @Override
    public FileMedia uploadFile(MultipartFile file) {
        FileMedia fileMedia = new FileMedia();
        fileMedia.setFilename(file.getOriginalFilename());
        fileMedia.setContentType(file.getContentType());
        fileMedia.setSize(file.getSize());
        fileMedia.setUploadDateTime(LocalDateTime.now());

        return fileMediaRepository.save(fileMedia);
    }

    @Override
    public FileMedia getFileById(Long fileId) {
        return fileMediaRepository.findById(fileId).orElse(null);
    }
}