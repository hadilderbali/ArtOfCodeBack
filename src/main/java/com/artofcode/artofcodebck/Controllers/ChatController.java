package com.artofcode.artofcodebck.Controllers;


import com.artofcode.artofcodebck.Entities.Message;
import com.artofcode.artofcodebck.Repositories.MessageRepo;
import com.artofcode.artofcodebck.Services.MessageImpl;
import com.artofcode.artofcodebck.Services.IMessage;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/feed")
public class ChatController {

    @Autowired
    IMessage iMessage;
    @Autowired
    MessageRepo messageRepo;
    @Autowired
    UserRepository users;
    @Autowired
    MessageImpl message;

    // @MessageMapping("/add")
    @PostMapping(value = "/add")
    public Message addMessage(@RequestBody Message message) {

        return iMessage.addMessage(message);
    }

    @PostMapping(value = "/add2/{id}/{ids}")
    public Message addMessage2(@RequestBody Message message, @PathVariable Integer id,@PathVariable Integer ids) {

        return iMessage.addMessage2(message, id , ids);
    }

    @GetMapping(value = "/showall")
    public List<Message> retrieveAllCourse() {

        return iMessage.retrieveAllCour();
    }

    @GetMapping(value = "/showalluser/{id}")
    public List<User> retrieveAllusers(@PathVariable Integer id ) {

        return iMessage.retrieveAlluser(id);
    }

    @GetMapping(value = "/showuser/{id}")
    public User retriveProb(@PathVariable Integer id) {
//Message msg = messageRepo.afficher(id).or
        return iMessage.retrieveById(id);
    }
@GetMapping(value="/adduser/{name}/{names}")
public User add(@PathVariable String  name ,@PathVariable String  names){

 return     users.login(name, names);

 //   if(logins.getUsername().equals(user.getUsername()) && logins.getPassword().equals(user.getPassword())){
      ///      return user ;
       // }else{
     //   return users.save(user);

   // }
}
    @GetMapping(value = "/showCourse/{id}")
    public List<Message> retrive(@PathVariable Integer id) {
        //  Message  ids = messageRepo.afficher(id);
        return messageRepo.afficher(id);
    }

    @GetMapping(value = "/affdate")
    public List<String> tri() {
        return messageRepo.tripardate();
    }

    @GetMapping(value = "/affdate2")
    public List<String> tri2() {
        return messageRepo.tripardate2();
    }

    @GetMapping(value = "/affdate33/{senderId}/{receiverId}")
    public List<Message> tri3(@PathVariable Integer senderId, @PathVariable Integer receiverId) {


        return messageRepo.findBySenderAndReceiverOrderByDate(senderId, receiverId);
    }

    @GetMapping(value = "/final")
    public String finals() {
        List<String> list1 = messageRepo.tripardate();
        List<String> list = messageRepo.tripardate2();
        // List<String>   lists= list1 list;
        return "Résultat 1 : " + list + "\nRésultat 2 : " + list1;
    }

    @GetMapping(value = "/login")
    public Integer logins(@RequestParam("param1") String param1, @RequestParam("param2") String param2) {
        User user = users.login(param1, param2);
        if (user.getFirstname().equals(param1) && user.getPassword().equals(param2)) {
          return   user.getId();
            //return user ;
        }else{
            return user.getId();
        }
    }
    @PostMapping(value = "/loginup")
    public User loginss(@RequestBody User user) {

        User logins = users.login(user.getFirstname(), user.getPassword());
        if (logins.getFirstname().equals(user.getFirstname()) && logins.getPassword().equals(user.getPassword())) {
            return user;
        }
     return user;
    }
    @PostMapping("/logins")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials) {
        String firstname = credentials.get("firstname");
        String password = credentials.get("password");

        if (message.verifierAuthentification(firstname, password)) {
            return new ResponseEntity<>("Authentification réussie", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Authentification échouée", HttpStatus.UNAUTHORIZED);
        }
    }

}