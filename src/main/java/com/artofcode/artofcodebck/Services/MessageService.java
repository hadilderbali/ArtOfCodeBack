package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.Message;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MessageService {
    Message sendMessage(Message message, List<MultipartFile> files);
    Message getMessageById(Long messageId);
}
