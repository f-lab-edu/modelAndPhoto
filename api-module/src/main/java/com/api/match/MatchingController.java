package com.api.match;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/matchings")
public class MatchingController {

    // 매칭 가능한 사용자 목록 조회
    @GetMapping
    public ResponseEntity<MatchingUsersResponse> getMatchingUsers() {
        List<MatchingUser> matches = List.of(
                new MatchingUser(456, "김철수", "MODEL", "Seoul", List.of("fashion", "portrait"), "안녕하세요 김철수입니다."),
                new MatchingUser(789, "뉴진스", "MODEL", "Busan", List.of("fashion", "portrait"), "안녕하세요 뉴진스입니다.")
        );
        MatchingUsersResponse response = new MatchingUsersResponse(matches);
        return ResponseEntity.ok(response);
    }

    // 매칭 요청 전송
    @PostMapping("/request")
    public ResponseEntity<MatchRequestResponse> sendMatchRequest(@RequestBody MatchRequest request) {
        return ResponseEntity.ok(new MatchRequestResponse("req_789", "pending"));
    }

    // 매칭 요청 목록 조회 (GET)
    @GetMapping("/requests")
    public ResponseEntity<MatchRequestsResponse> getMatchRequests() {
        List<MatchRequestDetail> requests = List.of(
                new MatchRequestDetail("req_789", "박성수", "PHOTOGRAPHER", "pending"),
                new MatchRequestDetail("req_456", "홍길동", "PHOTOGRAPHER", "pending")
        );
        MatchRequestsResponse response = new MatchRequestsResponse(requests);
        return ResponseEntity.ok(response);
    }

    // 매칭 요청 수락 또는 거절 응답 전송
    @PostMapping("/respond")
    public ResponseEntity<MatchResponse> respondToMatchRequest(@RequestBody MatchRespondRequest request) {
        return ResponseEntity.ok(new MatchResponse("accepted"));
    }
}