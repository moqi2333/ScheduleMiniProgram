package com.moqi.scheduleminiprogrambackend.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class MessageZone {

    private int messageZoneId;

    private String studentOpenId;

    private String topic;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    public MessageZone(int messageZoneId, String studentOpenId, String topic, Date createTime) {
        this.messageZoneId = messageZoneId;
        this.studentOpenId = studentOpenId;
        this.topic = topic;
        this.createTime = createTime;
    }

    public MessageZone() {
    }

    public MessageZone(String studentOpenId, String topic, Date createTime) {
        this.studentOpenId = studentOpenId;
        this.topic = topic;
        this.createTime = createTime;
    }

    public int getMessageZoneId() {
        return messageZoneId;
    }

    public void setMessageZoneId(int messageZoneId) {
        this.messageZoneId = messageZoneId;
    }

    public String getStudentOpenId() {
        return studentOpenId;
    }

    public void setStudentOpenId(String studentOpenId) {
        this.studentOpenId = studentOpenId;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
