package com.api.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "CONVERSATION")
@NoArgsConstructor
@Builder
public class ConversationEntity {
    @Id
    @Column(name = "conversation_id", length = 40)
    private String conversationId; // Primary Key

    @Column(name = "last_message_timestamp", nullable = false)
    private LocalDateTime lastMessageTimestamp; // 마지막 메시지 시간

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt; // 생성 시간

    @Column(name = "updated_at")
    @LastModifiedDate
    private LocalDateTime updatedAt; // 업데이트 시간

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "conversation_id")
    private List<ConversationParticipantEntity> participants;

    public ConversationEntity(String conversationId, LocalDateTime lastMessageTimestamp, LocalDateTime createdAt, LocalDateTime updatedAt, List<ConversationParticipantEntity> participants) {
        this.conversationId = conversationId;
        this.lastMessageTimestamp = lastMessageTimestamp;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.participants = participants;
    }
}
