package com.moqi.scheduleminiprogrambackend.service;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.vo.ResultVO;
import com.moqi.scheduleminiprogrambackend.vo.UserVO;

public interface UserService {

    /**
     * 用于小程序的登录
     * @param code 前端传入的code
     * @return 请求的返回体
     */
    JSONObject login(String code);

    /**
     * 完善用户信息
     * @param skey 登录凭证
     * @param name 用户姓名
     * @param studentId 用户学号
     * @param avatar 用户头像url
     * @return 规定的返回结构
     */
    JSONObject certification(String skey,String name,String studentId,String avatar);

    ResultVO<UserVO> getInformation(String skey);

    /*JSONObject updataInformation()*/

    /**
     * 获取所有注册学生的信息
     * @return 注册学生信息的列表
     */
    JSONObject getAllUsers();
}
