package com.api.dto.match;

import java.util.List;

public class MatchableUsersResponse {

    private final List<MatchableResponse> matches;

    public MatchableUsersResponse(List<MatchableResponse> matches) {
        this.matches = matches;
    }

    public List<MatchableResponse> getMatches() {
        return matches;
    }
}
