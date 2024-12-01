package com.api.repository;

import com.api.entity.UserEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserRepository {

    private Map<String, UserEntity> userEntityMap = new HashMap<>();

    public List<UserEntity> retrieveMatchableUsers(String sessionUserId) {
        // 매칭이 가능한 사용자 목록을 조회해야 합니다.
        // 사용자 목록 조회이므로 UserEntity를 조회
        List<UserEntity> userEntities = new ArrayList<>();

        return userEntities;
    }
}
