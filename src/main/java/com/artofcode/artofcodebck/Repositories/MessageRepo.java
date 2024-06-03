package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Message;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepo extends CrudRepository<Message,Long> {
    @Query(value = "select id from message ORDER BY id DESC LIMIT 1;", nativeQuery=true)
    Integer contenulast();
    // select contenu from message  ORDER BY DATE_FORMAT(date_envoi, '%Y-%m-%d %H:%i') ASC;
    @Query(value="select contenu from message where sender_id =1 ORDER BY DATE_FORMAT(date_envoi, '%Y-%m-%d %H:%i') ASC;",nativeQuery = true)
    List<String> tripardate();
    @Query(value="select contenu from message where sender_id=2 ORDER BY DATE_FORMAT(date_envoi, '%Y-%m-%d %H:%i') ASC;",nativeQuery = true)
    List<String> tripardate2();
    @Query(value="select * from message where (sender_id= :sender_id AND receiver_id= :receiver_id) OR (sender_id= :receiver_id " +
            "AND receiver_id= :sender_id) ORDER BY DATE_FORMAT(date_envoi, '%Y-%m-%d %H:%i') ASC;",nativeQuery = true)
    List<Message> findBySenderAndReceiverOrderByDate(@Param("sender_id") Integer sender_id, @Param("receiver_id") Integer receiver_id);
    @Query(value="select * from message where receiver_id= :receiver_id",nativeQuery = true)
    List<Message> afficher(@Param ("receiver_id")Integer receiver_id );
}
