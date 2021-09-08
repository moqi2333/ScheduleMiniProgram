package com.moqi.scheduleminiprogrambackend.po;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.moqi.scheduleminiprogrambackend.vo.ScheduleVO;
import org.springframework.lang.NonNull;

import java.sql.Date;
import java.sql.Time;

public class Schedule {

    private int scheduleId;

    private Date date;

    private Time startTime;

    private Time endTime;

    private String place;

    private String content;

    public Schedule(@NonNull ScheduleVO scheduleVO){
        this.scheduleId= scheduleVO.getScheduleId();
        this.date=scheduleVO.getDate();
        //实现HH:mm:ss和HH:mm之间的转换
        this.startTime=Time.valueOf(scheduleVO.getStartTime()+":00");
        this.endTime=Time.valueOf(scheduleVO.getEndTime()+":00");
        this.place=scheduleVO.getPlace();
        this.content=scheduleVO.getContent();
    }

    public Schedule(int scheduleId, Date date, Time startTime, Time endTime, String place, String content) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.content = content;
    }

    public Schedule() {
    }

    public int getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(int scheduleId) {
        this.scheduleId = scheduleId;
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
}
