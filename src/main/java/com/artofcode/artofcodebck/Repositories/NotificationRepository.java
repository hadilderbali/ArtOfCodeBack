package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification,Long> {
    List<Notification> findByUser_Id(int userId);

}
