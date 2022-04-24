package com.template.entity;

public class UserDTO {
    private String username;
    private String nickname;
    private Integer  userPortrait;

    public UserDTO(String username, String nickname, Integer userPortrait) {
        this.username = username;
        this.nickname = nickname;
        this.userPortrait = userPortrait;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getUserPortrait() {
        return userPortrait;
    }

    public void setUserPortrait(Integer userPortrait) {
        this.userPortrait = userPortrait;
    }
}
