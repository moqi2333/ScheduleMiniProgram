package com.moqi.scheduleminiprogrambackend.vo;

import com.moqi.scheduleminiprogrambackend.po.User;
import org.springframework.lang.NonNull;

public class UserVO {

    private String isTeacher;

    private String name;

    private String studentId;

    public UserVO(@NonNull User user){
        this.isTeacher = user.getIsTeacher()==1?"true":"false";
        this.name=user.getName();
        this.studentId=user.getStudentId();
    }

    public UserVO(String isTeacher, String name, String studentId) {
        this.isTeacher = isTeacher;
        this.name = name;
        this.studentId = studentId;
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
}
