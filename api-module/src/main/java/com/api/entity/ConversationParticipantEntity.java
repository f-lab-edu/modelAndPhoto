package com.api.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "conversation_participant",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"conversation_id", "user_id"})})
@NoArgsConstructor
@Builder
public class ConversationParticipantEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long conversationParticipantId;

    @Column(name = "conversation_id", nullable = false)
    private String conversationId;

    @Column(name = "user_id", nullable = false)
    private String userId;

    public ConversationParticipantEntity(Long conversationParticipantId, String conversationId, String userId) {
        this.conversationParticipantId = conversationParticipantId;
        this.conversationId = conversationId;
        this.userId = userId;
    }
}

