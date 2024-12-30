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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conversation_id", nullable = false)
    private ConversationEntity conversation;

    @Column(name = "user_id", nullable = false)
    private String userId;

    public ConversationParticipantEntity(Long conversationParticipantId, ConversationEntity conversation, String userId) {
        this.conversationParticipantId = conversationParticipantId;
        this.conversation = conversation;
        this.userId = userId;
    }
}

