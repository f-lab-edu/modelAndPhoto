package com.api.repository;

import com.api.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, String> {

    @Query("SELECT c FROM ConversationEntity c " +
            "WHERE JSON_CONTAINS(c.participantIds, :userId) = 1 " +
            "ORDER BY c.lastMessageTimestamp DESC")
    List<ConversationEntity> retrieve(@Param("userId") String sessionUserId);

    @Query("SELECT c FROM ConversationEntity c " +
            "WHERE JSON_CONTAINS(c.participantIds, :sender) = 1 " +
            "AND JSON_CONTAINS(c.participantIds, :receiver) = 1")
    Optional<String> getConversationId(
            @Param("sender") String senderId,
            @Param("receiver") String receiverId);

    @Override
    <S extends ConversationEntity> S save(S entity);

}
