package com.artofcode.artofcodebck.Services;



import com.artofcode.artofcodebck.Entities.Message;
import com.artofcode.artofcodebck.user.User;

import java.util.List;

public interface IMessage {
    public Message addMessage(Message message);

    public List<Message> retrieveAllCour();

    public Message retrieveCourseById(Long id);

    public List<User> retrieveAlluser(Integer id);
    public User retrieveById(Integer id);

    public Message addMessage2(Message message, Integer idrec, Integer idsend);
}