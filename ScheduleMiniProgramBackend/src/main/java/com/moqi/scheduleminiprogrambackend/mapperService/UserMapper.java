package com.moqi.scheduleminiprogrambackend.mapperService;

import com.moqi.scheduleminiprogrambackend.po.User;

import java.util.List;

public interface UserMapper {


    Integer insert(User user);

    Integer updateById(User user);

    User selectByOpenId(String openId);

    User selectBySkey(String skey);

    Integer updateInfoById(User user);

    User getTeacher(String name);

    List<User> getAllStudentUsers();

    List<User> getStudentByUserIds(List<Integer> userId);

    User getStudentByUserId(Integer userId);
}
