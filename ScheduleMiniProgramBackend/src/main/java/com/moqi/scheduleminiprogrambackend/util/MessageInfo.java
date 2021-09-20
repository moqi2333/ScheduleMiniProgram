package com.moqi.scheduleminiprogrambackend.util;

import com.moqi.scheduleminiprogrambackend.po.Message;
import com.moqi.scheduleminiprogrambackend.po.MessageZone;
import com.moqi.scheduleminiprogrambackend.po.User;
import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.List;

public class MessageInfo {

    private String studentName;

    private String studentId;

    private String topic;

    private String createTime;

    private List<Message> messageList;

    public MessageInfo() {
    }

    public MessageInfo(@NotNull MessageZone messageZone, User user, List<Message> messageList){
        this.studentName=user.getName();
        this.studentId=user.getStudentId();
        this.topic=messageZone.getTopic();
        this.createTime=new Timestamp(messageZone.getCreateTime().getTime()).toString().substring(0,16);
        this.messageList=messageList;
    }

    public MessageInfo(String studentName, String studentId, String topic, String createTime, List<Message> messageList) {
        this.studentName = studentName;
        this.studentId = studentId;
        this.topic = topic;
        this.createTime = createTime;
        this.messageList = messageList;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
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

    public List<Message> getMessageList() {
        return messageList;
    }

    public void setMessageList(List<Message> messageList) {
        this.messageList = messageList;
    }
}
