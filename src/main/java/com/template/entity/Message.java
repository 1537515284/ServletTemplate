package com.template.entity;

import java.util.Date;

public class Message {

    private Integer id;
    private Integer uid;
    private String content;
    private Date date;


    public Message(Integer uid, String content, Date date) {
        this.uid = uid;
        this.content = content;
        this.date = date;
    }

    public Message() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
