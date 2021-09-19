package com.moqi.scheduleminiprogrambackend.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class User {

    private int userId;

    private String openId;

    private String skey;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date lastVisitTime;

    private String sessionKey;

    private int isTeacher;

    private String name;

    private String studentId;

    private String nickName;

    private String profileUrl;

    private String gender;

    private String city;

    private String province;

    private String country;

    public User() {
    }

    public User(int userId, String openId, String skey, Date createTime, Date lastVisitTime, String sessionKey, int isTeacher, String name, String studentId, String nickName, String profileUrl, String gender, String city, String province, String country) {
        this.userId = userId;
        this.openId = openId;
        this.skey = skey;
        this.createTime = createTime;
        this.lastVisitTime = lastVisitTime;
        this.sessionKey = sessionKey;
        this.isTeacher = isTeacher;
        this.name = name;
        this.studentId = studentId;
        this.nickName = nickName;
        this.profileUrl = profileUrl;
        this.gender = gender;
        this.city = city;
        this.province = province;
        this.country = country;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getSkey() {
        return skey;
    }

    public void setSkey(String skey) {
        this.skey = skey;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public int getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(int isTeacher) {
        this.isTeacher = isTeacher;
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

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
