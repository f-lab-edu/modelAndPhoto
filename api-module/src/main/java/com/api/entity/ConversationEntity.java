package com.api.entity;

import com.api.util.StringListConverter;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Entity
@Table(name = "CONVERSATION")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ConversationEntity {

    @Id
    @Column(name = "conversation_id", length = 40)
    private String conversationId; // Primary Key

    @Convert(converter = StringListConverter.class)
    @Column(name = "participant_ids", columnDefinition = "json", nullable = false)
    private List<String> participantIds; // 대화 참여자 ID 리스트

    @Column(name = "last_message_timestamp", nullable = false)
    private LocalDateTime lastMessageTimestamp; // 마지막 메시지 시간

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt; // 업데이트 시간

    public ConversationEntity(String conversationId, List<String> participantIds, LocalDateTime lastMessageTimestamp, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.conversationId = conversationId;
        this.participantIds = participantIds;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
