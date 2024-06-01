package com.artofcode.artofcodebck.Services.Notification;

import com.artofcode.artofcodebck.Entities.Grades;
import com.artofcode.artofcodebck.Entities.Notification;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.Repositories.GradesRepository;
import com.artofcode.artofcodebck.Repositories.NotificationRepository;
import com.artofcode.artofcodebck.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private GradesRepository gradesRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendResultsNotificationToCandidates() {
        // Récupérer toutes les notes de candidature
        List         <Grades> gradesList = gradesRepository.findAll();

        // Trier les notes par classement, si nécessaire
        Collections.sort(gradesList, Comparator.comparing(Grades::getCandidacyGrade));

        // Trier les grades par ordre décroissant en fonction du candidacyGrade
        gradesList.sort(Comparator.comparing(Grades::getCandidacyGrade).reversed());

        int position = 1;
        for (Grades grade : gradesList) {
            String message;
            switch (position) {
                case 1:
                    message = "Congratulations ! Your are the champion with a score of " + grade.getCandidacyGrade();
                    break;
                case 2:
                    message = "Bravo! You came second with a score of " + grade.getCandidacyGrade();
                    break;
                case 3:
                    message = "Bravo! You came third with a score of " + grade.getCandidacyGrade();
                    break;
                default:
                    message = "Thank you for your candidacy, you came " + position + "with a score of " + grade.getCandidacyGrade();
                    break;
            }
            Notification notification = new Notification();
            notification.setMessage(message);
            notification.setUser(grade.getCompetitionCandidacy().getUser()); // Utilisateur destinataire de la notification
            notificationRepository.save(notification);

            // Envoie la notification via WebSocket
            messagingTemplate.convertAndSendToUser(notification.getUser().getUsername(), "/topic/notifications", notification.getMessage());

            position++;
        }
    }

    public List <Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

    public List <Notification> getNotificationsByUserId(int userId) {
        return notificationRepository.findByUser_Id(userId);
    }
}




