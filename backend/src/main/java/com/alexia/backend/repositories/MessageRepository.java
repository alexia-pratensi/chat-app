package com.alexia.backend.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.alexia.backend.models.Message;
import com.alexia.backend.models.User;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    // Find messages by source user ID
    List<Message> findBySourceUserId(Long sourceId);

    // Custom query to find users who sent messages to a specific agent
    @Query("SELECT DISTINCT u FROM Message m JOIN m.sourceUser u WHERE m.targetUser.id = :agentId AND u.role = 'USER'")
    List<User> findUsersWhoSentMessagesToAgent(Long agentId);

    // Custom query to find conversation messages between two users
    @Query("SELECT m FROM Message m WHERE (m.sourceUser.id = :userId AND m.targetUser.id = :targetUserId) OR (m.sourceUser.id = :targetUserId AND m.targetUser.id = :userId) ORDER BY m.createdAt ASC")
    List<Message> findConversationBetweenUsers(@Param("userId") Long userId, @Param("targetUserId") Long targetUserId);

}
