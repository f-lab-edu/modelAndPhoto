package com.api.repository;

import com.api.entity.MessageEntity;
import com.api.enums.MessageStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<MessageEntity, String> {

    // 특정 수신자의 메시지 상태 업데이트
    @Modifying
    @Query("UPDATE MessageEntity m " +
            "SET m.messageStatus = :status " +
            "WHERE m.conversationId = :conversationId " +
            "AND m.receiverId = :readerId")
    void updateMessageStatus(
            @Param("conversationId") String conversationId,
            @Param("readerId") String readerId,
            @Param("status") MessageStatus status);

    List<MessageEntity> findByConversationIdAndTimestampGreaterThanEqualAndTimestampLessThanEqual(
            String conversationId,
            LocalDateTime startTime,
            LocalDateTime endTime);

}
