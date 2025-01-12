package com.api.repository;

import com.api.entity.ConversationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<ConversationEntity, String> {

    List<ConversationEntity> findDistinctByParticipants(String sessionUserId);

}
