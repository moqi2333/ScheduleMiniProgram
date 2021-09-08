package com.moqi.scheduleminiprogrambackend.util;

import com.moqi.scheduleminiprogrambackend.po.Appointment;
import com.moqi.scheduleminiprogrambackend.po.User;

public class ExcelInfo {

    private String name;

    private String studentId;

    private String startTime;

    private String content;

    private String record;

    private String feedback;

    public ExcelInfo(String name, String studentId, String startTime, String content, String record, String feedback) {
        this.name = name;
        this.studentId = studentId;
        this.startTime = startTime;
        this.content = content;
        this.record = record;
        this.feedback = feedback;
    }

    public ExcelInfo(Appointment appointment, User user){
        this.name=user.getName();
        this.studentId=user.getStudentId();
        this.startTime=appointment.getDate().toString()+" "+appointment.getStartTime().toString().substring(0,5);
        this.content=appointment.getContent();
        this.record=appointment.getRecord();
        this.feedback=appointment.getFeedback();
    }

    public ExcelInfo() {
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

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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
}
