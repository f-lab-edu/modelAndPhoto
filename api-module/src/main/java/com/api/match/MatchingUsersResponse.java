package com.api.match;

import java.util.List;

public class MatchingUsersResponse {
    private List<MatchingUser> matches;

    public MatchingUsersResponse(List<MatchingUser> matches) {
        this.matches = matches;
    }

    public List<MatchingUser> getMatches() {
        return matches;
    }
}
