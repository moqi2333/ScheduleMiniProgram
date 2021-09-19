package com.moqi.scheduleminiprogrambackend.vo;

import com.moqi.scheduleminiprogrambackend.po.User;
import org.springframework.lang.NonNull;

public class UserVO {

    private int userId;

    private String isTeacher;

    private String name;

    private String studentId;

    private String avatar;

    public UserVO(@NonNull User user){
        this.userId=user.getUserId();
        this.isTeacher = user.getIsTeacher()==1?"true":"false";
        this.name=user.getName();
        this.studentId=user.getStudentId();
        this.avatar=user.getProfileUrl();
    }

    public UserVO(String isTeacher, String name, String studentId,int userId,String avatar) {
        this.isTeacher = isTeacher;
        this.name = name;
        this.studentId = studentId;
        this.userId=userId;
        this.avatar=avatar;
    }

    public UserVO() {
    }

    public String getIsTeacher() {
        return isTeacher;
    }

    public void setIsTeacher(String isTeacher) {
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

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
