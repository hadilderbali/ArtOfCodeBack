package com.artofcode.artofcodebck.Services;


import com.artofcode.artofcodebck.Entities.Message;
import com.artofcode.artofcodebck.Repositories.MessageRepo;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class MessageImpl implements IMessage {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    UserRepository users;

    @Override
    public Message addMessage(Message message){
        // User id = users.findById(receiver).orElse(null);
        Integer senders = 1 ;
        User send  = users.findById(senders).orElse(null);
//User recu = users.findById(idrec).orElse(null);
        message.setSenderId(send.getId());
        //message.setReceiver(recu);
        message.setDateEnvoi(new Date());
        //message.setSender(1L);
        // message.getSender().setIdUser(2L);
        // message.setSender(1L);

        return messageRepo.save(message);
    }
    @Override
    public Message addMessage2(Message message  ,Integer idrec ,Integer idsend  ){

        //Long senders = 1L ;
        User send  = users.findById(idsend).orElse(null);
        User recu = users.findById(idrec).orElse(null);
        message.setSenderId(send.getId());
        message.setRecipientId(recu.getId());;
        message.setDateEnvoi(new Date());
        //message.setSender(1L);
        // message.getSender().setIdUser(2L);
        // message.setSender(1L);

        return messageRepo.save(message);
    }
    @Override
    public List<Message> retrieveAllCour() {

        return (List<Message>) messageRepo.findAll();
    }



    @Override
    public Message retrieveCourseById(Long id) {

        return messageRepo.findById(id).get();
    }

    @Override
    public List<User> retrieveAlluser(Integer id) {

        return (List<User>) users.userconnect(id);
    }
    @Override
    public User retrieveById(Integer id) {

        return users.findById(id).get();
    }
    public boolean verifierAuthentification(String firstname, String password) {
        Optional<User> utilisateurOptional = users.findByFirstname(firstname);

        return utilisateurOptional.map(utilisateur -> utilisateur.getPassword().equals(password)).orElse(false);
    }



}