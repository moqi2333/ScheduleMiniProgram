package com.moqi.scheduleminiprogrambackend.vo;

import com.moqi.scheduleminiprogrambackend.po.Appointment;

import java.sql.Timestamp;

public class RecordVO {

    private String record;

    private String avatar;

    private String name;

    private String studentId;

    private String createTime;

    private String date;

    private String startTime;

    private String endTime;

    private String place;

    private String content;

    private String other;

    public RecordVO(String record, String avatar, String name, String studentId, String createTime, String date, String startTime, String endTime, String place, String content, String other) {
        this.record = record;
        this.avatar = avatar;
        this.name = name;
        this.studentId = studentId;
        this.createTime = createTime;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.content = content;
        this.other = other;
    }

    public RecordVO(Appointment appointment) {
        this.record=appointment.getRecord();
        this.createTime=appointment.getRecordTime()==null?null:new Timestamp(appointment.getRecordTime().getTime()).toString().substring(0,16);
        this.date=appointment.getDate().toString();
        this.startTime=appointment.getStartTime().toString().substring(0,5);
        this.endTime=appointment.getEndTime().toString().substring(0,5);
        this.place= appointment.getPlace();
        this.content=appointment.getContent();
        this.other=appointment.getOther();
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOther() {
        return other;
    }

    public void setOther(String other) {
        this.other = other;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
}
