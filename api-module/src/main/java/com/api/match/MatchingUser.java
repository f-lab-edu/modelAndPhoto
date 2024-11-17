package com.api.match;

import java.util.List;

public class MatchingUser {

    private int userId;
    private String name;
    private String role;
    private String location;
    private List<String> style;
    private String introduce;

    public MatchingUser() {}

    public MatchingUser(int userId, String name, String role, String location, List<String> style, String introduce) {
        this.userId = userId;
        this.name = name;
        this.role = role;
        this.location = location;
        this.style = style;
        this.introduce = introduce;
    }

    public int getUserId() {
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
