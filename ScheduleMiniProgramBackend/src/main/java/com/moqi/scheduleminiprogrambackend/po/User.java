package com.moqi.scheduleminiprogrambackend.po;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * The type User.
 */
public class User {

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

    /**
     * Instantiates a new User.
     *
     * @param openId        the open id
     * @param skey          the skey
     * @param createTime    the create time
     * @param lastVisitTime the last visit time
     * @param sessionKey    the session key
     * @param isTeacher     the is teacher
     * @param name          the name
     * @param studentId     the student id
     * @param nickName      the nick name
     * @param profileUrl    the profile url
     * @param gender        the gender
     * @param city          the city
     * @param province      the province
     * @param country       the country
     */
    public User(String openId, String skey, Date createTime, Date lastVisitTime, String sessionKey, int isTeacher, String name, String studentId, String nickName, String profileUrl, String gender, String city, String province, String country) {
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

    public String getOpenId() {
        return openId;
    }

    /**
     * Sets open id.
     *
     * @param openId the open id
     */
    public void setOpenId(String openId) {
        this.openId = openId;
    }

    /**
     * Gets skey.
     *
     * @return the skey
     */
    public String getSkey() {
        return skey;
    }

    /**
     * Sets skey.
     *
     * @param skey the skey
     */
    public void setSkey(String skey) {
        this.skey = skey;
    }

    /**
     * Gets create time.
     *
     * @return the create time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * Sets create time.
     *
     * @param createTime the create time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * Gets last visit time.
     *
     * @return the last visit time
     */
    public Date getLastVisitTime() {
        return lastVisitTime;
    }

    /**
     * Sets last visit time.
     *
     * @param lastVisitTime the last visit time
     */
    public void setLastVisitTime(Date lastVisitTime) {
        this.lastVisitTime = lastVisitTime;
    }

    /**
     * Gets session key.
     *
     * @return the session key
     */
    public String getSessionKey() {
        return sessionKey;
    }

    /**
     * Sets session key.
     *
     * @param sessionKey the session key
     */
    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    /**
     * Gets is teacher.
     *
     * @return the is teacher
     */
    public int getIsTeacher() {
        return isTeacher;
    }

    /**
     * Sets is teacher.
     *
     * @param isTeacher the is teacher
     */
    public void setIsTeacher(int isTeacher) {
        this.isTeacher = isTeacher;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets student id.
     *
     * @return the student id
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     * Sets student id.
     *
     * @param studentId the student id
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     * Gets nick name.
     *
     * @return the nick name
     */
    public String getNickName() {
        return nickName;
    }

    /**
     * Sets nick name.
     *
     * @param nickName the nick name
     */
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    /**
     * Gets profile url.
     *
     * @return the profile url
     */
    public String getProfileUrl() {
        return profileUrl;
    }

    /**
     * Sets profile url.
     *
     * @param profileUrl the profile url
     */
    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets city.
     *
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets city.
     *
     * @param city the city
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * Gets province.
     *
     * @return the province
     */
    public String getProvince() {
        return province;
    }

    /**
     * Sets province.
     *
     * @param province the province
     */
    public void setProvince(String province) {
        this.province = province;
    }

    /**
     * Gets country.
     *
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * Sets country.
     *
     * @param country the country
     */
    public void setCountry(String country) {
        this.country = country;
    }
}
