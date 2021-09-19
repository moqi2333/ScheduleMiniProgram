package com.moqi.scheduleminiprogrambackend.vo;

import com.moqi.scheduleminiprogrambackend.po.MessageZone;
import com.moqi.scheduleminiprogrambackend.po.User;

import java.sql.Timestamp;

public class MessageZoneVO {

    private int messageZoneId;

    private String name;

    private String studentId;

    private String avatar;

    private String topic;

    private String createTime;

    private int newMessageNum;

    public MessageZoneVO(MessageZone messageZone, User user,int newMessageNum){
        this.messageZoneId=messageZone.getMessageZoneId();
        this.name=user.getName();
        this.studentId=user.getStudentId();
        this.avatar=user.getProfileUrl();
        this.topic=messageZone.getTopic();
        this.createTime=new Timestamp(messageZone.getCreateTime().getTime()).toString().substring(0,16);
        this.newMessageNum=newMessageNum;
    }

    public MessageZoneVO() {
    }

    public MessageZoneVO(int messageZoneId, String name, String studentId, String avatar, String topic, String createTime, int newMessageNum) {
        this.messageZoneId = messageZoneId;
        this.name = name;
        this.studentId = studentId;
        this.avatar = avatar;
        this.topic = topic;
        this.createTime = createTime;
        this.newMessageNum = newMessageNum;
    }

    public int getMessageZoneId() {
        return messageZoneId;
    }

    public void setMessageZoneId(int messageZoneId) {
        this.messageZoneId = messageZoneId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getNewMessageNum() {
        return newMessageNum;
    }

    public void setNewMessageNum(int newMessageNum) {
        this.newMessageNum = newMessageNum;
    }
}
