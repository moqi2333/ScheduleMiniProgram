package com.moqi.scheduleminiprogrambackend.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Message {

    private int messageId;

    //1为老师to学生，2为学生to老师
    private int type;

    private String content;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    private int messageZoneId;

    private int isReaded;

    public Message(int type, String content, Date createTime, int messageZoneId, int isReaded) {
        this.type = type;
        this.content = content;
        this.createTime = createTime;
        this.messageZoneId = messageZoneId;
        this.isReaded = isReaded;
    }
    public Message(int messageId, int type, String content, Date createTime, int messageZoneId, int isReaded) {
        this.messageId = messageId;
        this.type = type;
        this.content = content;
        this.createTime = createTime;
        this.messageZoneId = messageZoneId;
        this.isReaded = isReaded;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getMessageZoneId() {
        return messageZoneId;
    }

    public void setMessageZoneId(int messageZoneId) {
        this.messageZoneId = messageZoneId;
    }

    public int getIsReaded() {
        return isReaded;
    }

    public void setIsReaded(int isReaded) {
        this.isReaded = isReaded;
    }
}
