package com.api.match.service;

import com.api.match.dto.MatchRespondRequest;
import com.api.match.dto.MatchableUser;
import com.api.match.dto.MatchingCreationRequest;
import com.api.match.dto.MatchingRequest;
import com.api.match.entity.MatchingEntity;
import com.api.match.entity.UserEntity;
import com.api.match.enums.MatchingStatus;
import com.api.match.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;

    public MatchService(MatchRepository matchRepository) {
        this.matchRepository = matchRepository;
    }

    public List<MatchableUser> retrieveMatchableUsers(String sessionUserId) {
        List<UserEntity> userEntities = matchRepository.retrieveMatchableUsers(sessionUserId);

        List<MatchableUser> matchableUsers = new ArrayList<>();

        for (UserEntity userEntity : userEntities) {
            matchableUsers.add(new MatchableUser(userEntity.getUserId(), userEntity.getName(), userEntity.getRole(), userEntity.getLocation(), userEntity.getStyle(), userEntity.getIntroduce()));
        }

        return matchableUsers;
    }

    public void sendRequestMatch(MatchingCreationRequest request) {
        matchRepository.save(new MatchingEntity("req_123", request.getSenderId(), "요청자이름", request.getReceiverId(), "수신자이름", MatchingStatus.PENDING, LocalDateTime.now(), LocalDateTime.now(), "매칭수락 부탁드립니다."));
    }

    public List<MatchingRequest> retrieveMatchingRequests(String sessionUserId) {
        List<MatchingEntity> matchingEntities = matchRepository.retrieveMatchingRequests(sessionUserId);

//        matchingEntities.add(new MatchingEntity("req_789", "user_001", "윈터", "user_789", "박성수", MatchingStatus.PENDING, LocalDateTime.now(), LocalDateTime.now(), "매칭 요청 수락해주세요!"));
//        matchingEntities.add(new MatchingEntity("req_789", "user_002", "카리나", "user_789", "박성수", MatchingStatus.PENDING, LocalDateTime.now(), LocalDateTime.now(), "매칭 요청 수락해주세요!"));

        List<MatchingRequest> matchingRequests = new ArrayList<>();

        for (MatchingEntity matchingEntity : matchingEntities) {
            matchingRequests.add(new MatchingRequest(matchingEntity.getMatchingId(), matchingEntity.getSenderName(), matchRepository.getUserRole(matchingEntity.getSenderId()), matchingEntity.getStatus()));
        }

        return matchingRequests;
    }

    public void respondToMatchRequest(MatchRespondRequest matchRespondRequest) {
        MatchingEntity matchingEntity = matchRepository.getMatching(matchRespondRequest.getRequestId());

        matchingEntity.setStatus(matchRespondRequest.getMatchingStatus());
        matchingEntity.setUpdatedAt(LocalDateTime.now());

        matchRepository.save(matchingEntity);
    }
}