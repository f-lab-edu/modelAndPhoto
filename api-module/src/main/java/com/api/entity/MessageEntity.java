package com.api.entity;

import com.api.enums.MessageStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity
@Table(name = "MESSAGE")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MessageEntity {
    @Id
    @Column(name = "message_id", length = 40, nullable = false)
    private String messageId;

    @Column(name = "conversation_id", length = 40, nullable = false)
    private String conversationId;

    @Column(name = "sender_id", length = 40, nullable = false)
    private String senderId;

    @Column(name = "receiver_id", length = 40, nullable = false)
    private String receiverId;

    @Column(name = "file_id")
    private String fileId;

    @Column(name = "message_content")
    private String messageContent;

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "message_status", nullable = false)
    private MessageStatus messageStatus;

    public MessageEntity(String messageId, String conversationId, String senderId, String receiverId, String fileId, String messageContent, LocalDateTime timestamp, MessageStatus messageStatus) {
        this.messageId = messageId;
        this.conversationId = conversationId;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.fileId = fileId;
        this.messageContent = messageContent;
        this.timestamp = timestamp;
        this.messageStatus = messageStatus;
    }

}
