package com.api.repository;

import com.api.entity.MatchingEntity;
import com.api.entity.UserEntity;
import com.api.enums.UserRole;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MatchRepository {

    private Map<String, MatchingEntity> matchingEntityMap = new HashMap<>();

    public List<UserEntity> retrieveMatchableUsers(String sessionUserId) {
        // 매칭이 가능한 사용자 목록을 조회해야 합니다.
        // 사용자 목록 조회이므로 UserEntity를 조회
        List<UserEntity> userEntities = new ArrayList<>();

        userEntities.add(new UserEntity("user_456", "김철수", "MODEL", "Seoul", List.of("fashion", "portrait"), "안녕하세요 김철수입니다."));
        userEntities.add(new UserEntity("user_789", "뉴진스", "MODEL", "Busan", List.of("fashion", "portrait"), "안녕하세요 뉴진스입니다."));

        return userEntities;

    }

    public void save(MatchingEntity matching) {
        matchingEntityMap.put(matching.getMatchingId(), matching);
    }

    public List<MatchingEntity> retrieveMatchingRequests(String sessionUserId) {
        // todo sessionUserId
        return matchingEntityMap.values().stream()
                .filter(entity -> entity.getReceiverId().equals(sessionUserId))
                .collect(Collectors.toList());
    }

    public UserRole getUserRole(String userId) {
        return UserRole.MODEL;
    }

    public MatchingEntity getMatching(String requestId) {
        return matchingEntityMap.values()
                .stream()
                .filter(entity -> entity.getMatchingId().equals(requestId))
                .findAny().orElseThrow(NoSuchElementException::new);
    }
}
