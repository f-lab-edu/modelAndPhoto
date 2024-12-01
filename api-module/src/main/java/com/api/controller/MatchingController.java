package com.api.controller;

import com.api.dto.match.*;
import com.api.enums.MatchingStatus;
import com.api.dto.match.MatchRespondResponse;
import com.api.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/matchings")
public class MatchingController {

    private final MatchService matchService;

    public MatchingController(MatchService matchService) {
        this.matchService = matchService;
    }

    private final String SESSION_USER_ID = "PHO_001";

    /**
     * 매칭 가능한 유저 목록 조회
     * @return MatchableUsersResponse
     */
    @GetMapping
    public ResponseEntity<MatchableUsersResponse> retrieveMatchingUsers() {

        List<MatchableUser> matchableUsers = matchService.retrieveMatchableUsers(SESSION_USER_ID);// 세션사용자 Id로 조회

        List<MatchableResponse> matchingUsersResponses = new ArrayList<>();

        for (MatchableUser matchableUser : matchableUsers) {
            matchingUsersResponses.add(new MatchableResponse(matchableUser.getUserId()
                                                                , matchableUser.getName()
                                                                , matchableUser.getRole()
                                                                , matchableUser.getLocation()
                                                                , matchableUser.getStyle()
                                                                , matchableUser.getIntroduce()));
        }

        return ResponseEntity.ok(new MatchableUsersResponse(matchingUsersResponses));
    }

    /**
     * 매칭요청 전송
     * @param request
     * @return
     */
    @PostMapping("/request")
    public ResponseEntity<MatchRequestResponse> sendMatchRequest(@RequestBody @Valid MatchingRequestDto request) {

        MatchingCreationResponse matchingCreationResponse = matchService.sendRequestMatch(new MatchingCreationRequest(request.getSenderId(), request.getReceiverId(), request.getMessage()));

        return ResponseEntity.ok(new MatchRequestResponse(matchingCreationResponse.getMatchRequestId(), matchingCreationResponse.getStatus()));
    }

    /**
     * 매칭요청 목록 조회
     * @return
     */
    @GetMapping("/requests")
    public ResponseEntity<MatchRequestsResponse> retrieveMatchRequests() {

        List<MatchingRequest> matchingRequests = matchService.retrieveMatchingRequests(SESSION_USER_ID);

        return ResponseEntity.ok(new MatchRequestsResponse(matchingRequests));
    }

    /**
     * 매칭 요청 수락 또는 거절 응답 전송
     * @param request
     * @return
     */
    @PostMapping("/respond")
    public ResponseEntity<MatchResponse> respondToMatchRequest(@RequestBody @Valid MatchRespondRequestDto request) {

        MatchRespondResponse matchRespondResponse = matchService.respondToMatchRequest(new MatchRespondRequest(request.getRequestId(), request.getResponse()));

        return ResponseEntity.ok(new MatchResponse(matchRespondResponse.getMatchRequestId(), matchRespondResponse.getStatus()));
    }
}