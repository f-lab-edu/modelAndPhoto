package com.api.service;

import com.api.dto.match.*;
import com.api.entity.MatchingEntity;
import com.api.entity.UserEntity;
import com.api.enums.MatchingStatus;
import com.api.enums.UserRole;
import com.api.repository.MatchRepository;
import com.api.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MatchServiceTest {

    @InjectMocks
    private MatchService matchService;

    @Mock
    private MatchRepository matchRepository;

    @Mock
    private UserRepository userRepository;

    @Test
    @DisplayName("매칭가능한 유저 목록을 조회한다. 매칭목록의 유저는 본인과 다른 Role이어야 한다.")
    void test_retrieve_matchable_users() {
        // given
        String sessionUserId = "PHR_123";
        UserRole sessionUserRole = UserRole.PHOTOGRAPHER;

        doReturn(getUserEntities())
                .when(userRepository).retrieveMatchableUsers(any(String.class));

        // when
        List<MatchableUser> matchableUsers = matchService.retrieveMatchableUsers(sessionUserId);

        // then
        matchableUsers.forEach(matchableUser -> {
            assertThat(matchableUser.getUserId()).isNotNull();
            assertThat(matchableUser.getRole()).isNotEqualTo(sessionUserRole);
        });

        // verify
        verify(userRepository).retrieveMatchableUsers(any(String.class));

    }

    @Test
    @DisplayName("매칭요청을 전송한다. 전송시엔 상태값이 PENDING")
    void test_send_request_match() {
        // given
        doReturn(new MatchingEntity("MAT_001", "MOD_001", "뉴진스",
                "PHO_001", "박성수", MatchingStatus.PENDING, LocalDateTime.now(), LocalDateTime.now(), "매칭수락 부탁드립니다."))
                .when(matchRepository).save(any(MatchingEntity.class));

        // when
        MatchingCreationResponse matchingCreationResponse = matchService.sendRequestMatch(new MatchingCreationRequest("MOD_001", "PHO_001", "매칭수락 부탁드립니다."));

        // then
        assertThat(matchingCreationResponse.getMatchRequestId()).isNotNull();
        assertThat(matchingCreationResponse.getStatus()).isEqualTo(MatchingStatus.PENDING);

        // verify
        verify(matchRepository, times(1)).save(any(MatchingEntity.class));
    }

    @Test
    @DisplayName("매칭요청 목록을 조회한다. 매칭요청 목록의 SenderRole은 본인의 Role과 같을 수 없다.")
    void test_retrieve_matching_requests() {
        // given
        String sessionUserId = "PHO_123";
        UserRole sessionUserRole = UserRole.PHOTOGRAPHER;
        doReturn(getMatchingEntities()).when(matchRepository).retrieveMatchingRequests(any(String.class));

        // when
        List<MatchingRequest> matchingRequests = matchService.retrieveMatchingRequests(sessionUserId);

        // then
        matchingRequests.forEach(matchingRequest -> {
            assertThat(matchingRequest.getRequestId()).isNotNull();
            assertThat(matchingRequest.getSenderRole()).isNotEqualTo(sessionUserRole);
        });

        // verify
        verify(matchRepository, times(1)).retrieveMatchingRequests(any(String.class));
    }

    @Test
    @DisplayName("매칭 요청 수락 또는 거절 응답 전송")
    void test_response_to_match_request() {
        // given
        MatchRespondRequest matchRespondRequest = new MatchRespondRequest("MAT_001", MatchingStatus.ACCEPTED);

        doReturn(new MatchingEntity(matchRespondRequest.getRequestId(), "MOD_001", "홍길동", "PHO_001", "박성수", MatchingStatus.PENDING, LocalDateTime.now(), LocalDateTime.now(), "매칭 요청 합니다."))
                .when(matchRepository).getMatching(any(String.class));
        doReturn(new MatchingEntity(matchRespondRequest.getRequestId(), "MOD_001", "홍길동", "PHO_001", "박성수", matchRespondRequest.getMatchingStatus(), LocalDateTime.now(), LocalDateTime.now(), "매칭 요청 합니다."))
                .when(matchRepository).save(any(MatchingEntity.class));

        // when
        MatchRespondResponse matchRespondResponse = matchService.respondToMatchRequest(matchRespondRequest);

        // then
        assertThat(matchRespondResponse.getMatchRequestId()).isNotNull();
        assertThat(matchRespondResponse.getStatus()).isNotNull();

        // verify
        verify(matchRepository, times(1)).getMatching(any(String.class));
        verify(matchRepository, times(1)).save(any(MatchingEntity.class));
    }

    private static List<MatchingEntity> getMatchingEntities() {
        return List.of(new MatchingEntity("MAT_001", "MOD_001", "윈터", "PHO_001", "박성수", MatchingStatus.PENDING, LocalDateTime.now(), LocalDateTime.now(), "매칭 요청 수락해주세요!"),
                new MatchingEntity("MAT_002", "MOD_002", "카리나", "PHO_001", "박성수", MatchingStatus.PENDING, LocalDateTime.now(), LocalDateTime.now(), "매칭 요청 수락해주세요!"));
    }


    private static List<UserEntity> getUserEntities() {
        return List.of(new UserEntity("MOD_123", "홍길동", UserRole.MODEL, "부산", List.of("fashion", "portrait"), "안녕하세요 홍길동입니다."),
                new UserEntity("MOD_124", "뉴진스", UserRole.MODEL, "부산", List.of("fashion", "portrait"), "안녕하세요 뉴진스입니다."));
    }
}