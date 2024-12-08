package com.api.dto.match;

import com.api.enums.UserRole;

import java.util.List;

public class MatchableUser {

    private String userId;
    private String name;
    private UserRole role;
    private String location;
    private List<String> style;
    private String introduce;

    public MatchableUser(String userId, String name, UserRole role, String location, List<String> style, String introduce) {
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

    public UserRole getRole() {
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
