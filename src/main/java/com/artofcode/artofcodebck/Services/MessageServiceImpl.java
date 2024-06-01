package com.artofcode.artofcodebck.Services;

import com.artofcode.artofcodebck.Entities.FileMedia;
import com.artofcode.artofcodebck.Entities.Message;
import com.artofcode.artofcodebck.Repositories.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class MessageServiceImpl {
    @Autowired
    private  MessageRepository messageRepository;



     public List<Message> getMessagesBetweenUsers(Long senderId, Long recipientId) {
        return messageRepository.findBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestamp(
                senderId, recipientId, recipientId, senderId);
    }

    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }
}
