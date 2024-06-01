package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    List<Message> findBySenderIdAndRecipientIdOrSenderIdAndRecipientIdOrderByTimestamp(
            Long senderId1, Long recipientId1, Long senderId2, Long recipientId2);

}
