package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Message;
import com.artofcode.artofcodebck.Services.MessageService;
import com.artofcode.artofcodebck.Services.MessageServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@RestController
@CrossOrigin("*")
public class MessageController {


    @Autowired

    private  SimpMessagingTemplate messagingTemplate;
    @Autowired

    private  MessageServiceImpl messageService;

    @GetMapping("/messages/{senderId}/{recipientId}")
    public List<Message> getMessagesBetweenUsers(@PathVariable Long senderId, @PathVariable Long recipientId) {
        return messageService.getMessagesBetweenUsers(senderId, recipientId);
    }

    @PostMapping("/messages")
    public void sendMessage(@RequestBody Message message) {
        System.out.println("Received message: " + message.getContent());
        System.out.println("Sender ID: " + message.getSenderId());
        System.out.println("Recipient ID: " + message.getRecipientId());
        messageService.sendMessage(message);

        messagingTemplate.convertAndSend("/topic/messages/" + message.getRecipientId(), message);
        System.out.println("Message sent to recipient ID: " + message.getRecipientId());
    }
}