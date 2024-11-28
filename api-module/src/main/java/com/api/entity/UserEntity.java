package com.api.entity;

import java.util.List;

public class UserEntity {

    public String userId;
    public String name;
    public String role;
    public String location;
    public List<String> style;
    public String introduce;

    public UserEntity(String userId, String name, String role, String location, List<String> style, String introduce) {
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
