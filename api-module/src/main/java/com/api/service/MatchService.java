package com.api.service;

import com.api.dto.match.*;
import com.api.entity.MatchingEntity;
import com.api.entity.UserEntity;
import com.api.enums.MatchingStatus;
import com.api.repository.MatchRepository;
import com.api.repository.UserRepository;
import com.api.util.IdGenerator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final UserRepository userRepository;

    public MatchService(MatchRepository matchRepository, UserRepository userRepository) {
        this.matchRepository = matchRepository;
        this.userRepository = userRepository;
    }

    public List<MatchableUser> retrieveMatchableUsers(String sessionUserId) {
        List<UserEntity> userEntities = userRepository.retrieveMatchableUsers(sessionUserId);

        List<MatchableUser> matchableUsers = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            matchableUsers.add(new MatchableUser(userEntity.getUserId(), userEntity.getName(), userEntity.getRole(), userEntity.getLocation(), userEntity.getStyle(), userEntity.getIntroduce()));
        }

        return matchableUsers;
    }

    public MatchingCreationResponse sendRequestMatch(MatchingCreationRequest request) {
        MatchingEntity savedMatch = matchRepository.save(new MatchingEntity(IdGenerator.generateId(IdGenerator.getPrefixMatching()), request.getSenderId(), "요청자이름", request.getReceiverId(), "수신자이름", MatchingStatus.PENDING, LocalDateTime.now(), LocalDateTime.now(), request.getMessage()));

        return new MatchingCreationResponse(savedMatch.getMatchingId(), savedMatch.getStatus());
    }

    public List<MatchingRequest> retrieveMatchingRequests(String sessionUserId) {
        List<MatchingEntity> matchingEntities = matchRepository.retrieveMatchingRequests(sessionUserId);

        List<MatchingRequest> matchingRequests = new ArrayList<>();

        for (MatchingEntity matchingEntity : matchingEntities) {
            matchingRequests.add(new MatchingRequest(matchingEntity.getMatchingId(), matchingEntity.getSenderName(), matchRepository.getUserRole(matchingEntity.getSenderId()), matchingEntity.getStatus()));
        }

        return matchingRequests;
    }

    public MatchRespondResponse respondToMatchRequest(MatchRespondRequest matchRespondRequest) {
        MatchingEntity matchingEntity = matchRepository.getMatching(matchRespondRequest.getRequestId());

        matchingEntity.setStatus(matchRespondRequest.getMatchingStatus());
        matchingEntity.setUpdatedAt(LocalDateTime.now());

        MatchingEntity updatedMatchingEntity = matchRepository.save(matchingEntity);
        return new MatchRespondResponse(updatedMatchingEntity.getMatchingId(), updatedMatchingEntity.getStatus());
    }
}