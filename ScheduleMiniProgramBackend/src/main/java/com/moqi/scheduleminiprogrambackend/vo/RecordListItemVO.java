package com.moqi.scheduleminiprogrambackend.vo;

import com.moqi.scheduleminiprogrambackend.po.Appointment;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class RecordListItemVO {

    private int appointmentId;

    private String name;

    private String studentId;

    private String avatar;

    private Date date;

    private Time startTime;

    private Time endTime;

    private String createTime;

    private String place;

    private String content;

    public RecordListItemVO(int appointmentId, String name, String studentId, String avatar, Date date, Time startTime, Time endTime, String createTime, String place, String content) {
        this.appointmentId = appointmentId;
        this.name = name;
        this.studentId = studentId;
        this.avatar = avatar;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.createTime = createTime;
        this.place = place;
        this.content = content;
    }

    public RecordListItemVO(Appointment appointment) {
        this.appointmentId=appointment.getAppointmentId();
        this.date=appointment.getDate();
        this.startTime=appointment.getStartTime();
        this.endTime=appointment.getEndTime();
        this.createTime=appointment.getRecordTime()==null?null:new Timestamp(appointment.getRecordTime().getTime()).toString().substring(0,16);
        this.place=appointment.getPlace();
        this.content=appointment.getContent();
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }
}
