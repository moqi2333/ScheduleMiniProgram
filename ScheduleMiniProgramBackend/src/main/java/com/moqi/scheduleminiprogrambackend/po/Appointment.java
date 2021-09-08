package com.moqi.scheduleminiprogrambackend.po;

import cn.hutool.core.date.DateTime;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.sql.Time;

public class Appointment {

    private int appointmentId;

    private Date date;

    private Time startTime;

    private Time endTime;

    private String place;

    private String content;

    private String other;

    private String studentOpenId;

    private int status;

    private String record;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date recordTime;

    private String feedback;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date feedbackTime;

    public Appointment() {
        this.status=0;
    }

    public Appointment(int appointmentId, Date date, Time startTime, Time endTime, String place, String content, String other, String studentOpenId, int status, String record, Date recordTime, String feedback, Date feedbackTime) {
        this.appointmentId = appointmentId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.content = content;
        this.other = other;
        this.studentOpenId = studentOpenId;
        this.status = status;
        this.record = record;
        this.recordTime = recordTime;
        this.feedback = feedback;
        this.feedbackTime = feedbackTime;
    }

    public Appointment(Date date, Time startTime, Time endTime, String place, String content, String other, String studentOpenId) {
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.content = content;
        this.other = other;
        this.studentOpenId=studentOpenId;
        this.status=0;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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

    public String getStudentOpenId() {
        return studentOpenId;
    }

    public void setStudentOpenId(String studentOpenId) {
        this.studentOpenId = studentOpenId;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getRecord() {
        return record;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public java.util.Date getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(java.util.Date recordTime) {
        this.recordTime = recordTime;
    }

    public java.util.Date getFeedbackTime() {
        return feedbackTime;
    }

    public void setFeedbackTime(java.util.Date feedbackTime) {
        this.feedbackTime = feedbackTime;
    }
}
