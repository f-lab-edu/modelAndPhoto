package com.api.match.dto;

import java.util.List;

public class MatchingUser {

    private String userId;
    private String name;
    private String role;
    private String location;
    private List<String> style;
    private String introduce;

    public MatchingUser(String userId, String name, String role, String location, List<String> style, String introduce) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.location = location;
        this.style = style;
        this.introduce = introduce;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getLocation() {
        return location;
    }

    public List<String> getStyle() {
        return style;
    }

    public String getIntroduce() {
        return introduce;
    }
}
