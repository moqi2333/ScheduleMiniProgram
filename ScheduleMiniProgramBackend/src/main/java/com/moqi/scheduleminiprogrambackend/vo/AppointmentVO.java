package com.moqi.scheduleminiprogrambackend.vo;

import com.moqi.scheduleminiprogrambackend.po.Appointment;
import com.moqi.scheduleminiprogrambackend.po.User;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.sql.Time;

public class AppointmentVO {

    private int appointmentId;

    private String name;

    private String studentId;

    private String avatar;

    private Date date;

    private String startTime;

    private String endTime;

    private String place;

    private String content;

    private String other;

    private String isVisible;

    public AppointmentVO(@NonNull Appointment appointment){
        this.appointmentId=appointment.getAppointmentId();
        this.date=appointment.getDate();
        this.startTime=appointment.getStartTime().toString().substring(0,5);
        this.endTime=appointment.getEndTime().toString().substring(0,5);
        this.place=appointment.getPlace();
        this.content=appointment.getContent();
        this.other=appointment.getOther();
    }

    public AppointmentVO(@NonNull Appointment appointment, User user, String isVisible){
        this.appointmentId=appointment.getAppointmentId();
        this.date=appointment.getDate();
        this.startTime=appointment.getStartTime().toString().substring(0,5);
        this.endTime=appointment.getEndTime().toString().substring(0,5);
        this.place=appointment.getPlace();
        this.content=appointment.getContent();
        this.other=appointment.getOther();
        this.name=user.getName();
        this.studentId=user.getStudentId();
        this.isVisible=isVisible;
    }

    public AppointmentVO(int appointmentId, String name, String studentId, String avatar, Date date, String startTime, String endTime, String place, String content, String other) {
        this.appointmentId = appointmentId;
        this.name = name;
        this.studentId = studentId;
        this.avatar = avatar;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.content = content;
        this.other = other;
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

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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

    public String getIsVisible() {
        return isVisible;
    }

    public void setIsVisible(String isVisible) {
        this.isVisible = isVisible;
    }
}
