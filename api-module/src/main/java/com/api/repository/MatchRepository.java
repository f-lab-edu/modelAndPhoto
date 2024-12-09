package com.api.repository;

import com.api.entity.MatchingEntity;
import com.api.enums.UserRole;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class MatchRepository {

    private Map<String, MatchingEntity> matchingEntityMap = new HashMap<>();

    public MatchingEntity save(MatchingEntity matching) {
        return matchingEntityMap.put(matching.getMatchingId(), matching);
    }

    public List<MatchingEntity> retrieveMatchingRequests(String sessionUserId) {
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
