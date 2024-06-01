package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.Notification;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.Services.Notification.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notifications")
@CrossOrigin("*")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    // Endpoint pour envoyer les notifications aux candidats
    @GetMapping("/sendResults")
    public ResponseEntity<String> sendResultsNotification() {
        try {
            notificationService.sendResultsNotificationToCandidates();

            // Envoie un message WebSocket à tous les utilisateurs concernés
            messagingTemplate.convertAndSend("/topic/notifications", "Nouvelle notification envoyée");

            return new ResponseEntity<>("Les notifications ont été envoyées avec succès", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Erreur lors de l'envoi des notifications: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getall")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/getuser/{userid}/notifications")
    public ResponseEntity<List<Notification>> getNotificationsByUserId(@PathVariable("userid") int userId) {
        List<Notification> notifications = notificationService.getNotificationsByUserId(userId);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping("getuser/{userid}")
    public ResponseEntity<User> getUserById(@PathVariable("userid") int userId) {
        Optional<User> userOptional = notificationService.getUserById(userId);
        return userOptional.map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
 