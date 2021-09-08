package com.moqi.scheduleminiprogrambackend.vo;

import com.moqi.scheduleminiprogrambackend.po.Schedule;
import org.springframework.lang.NonNull;
import springfox.documentation.spring.web.json.Json;

import java.sql.Date;

public class ScheduleVO {

    private int scheduleId;

    private Date date;

    private String startTime;

    private String endTime;

    private String place;

    private String content;

    public ScheduleVO(@NonNull Schedule schedule){
        this.scheduleId= schedule.getScheduleId();
        this.date=schedule.getDate();

        //实现HH:mm:ss和HH:mm之间的转换
        this.startTime=schedule.getStartTime().toString().substring(0,5);
        this.endTime=schedule.getEndTime().toString().substring(0,5);

        this.place=schedule.getPlace();
        this.content=schedule.getContent();
    }

    public ScheduleVO(int scheduleId, Date date, String startTime, String endTime, String place, String content) {
        this.scheduleId = scheduleId;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.place = place;
        this.content = content;
    }

    public ScheduleVO() {
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
}
