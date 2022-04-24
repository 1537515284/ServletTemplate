package com.template.entity;

public class MessageDTO {

    private String sender_username;
    private String sender_nickname;
    private String content;
    private String time;

    public MessageDTO(String sender_username, String sender_nickname, String content, String time) {
        this.sender_username = sender_username;
        this.sender_nickname = sender_nickname;
        this.content = content;
        this.time = time;
    }

    public String getSender_username() {
        return sender_username;
    }

    public void setSender_username(String sender_username) {
        this.sender_username = sender_username;
    }

    public String getSender_nickname() {
        return sender_nickname;
    }

    public void setSender_nickname(String sender_nickname) {
        this.sender_nickname = sender_nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
