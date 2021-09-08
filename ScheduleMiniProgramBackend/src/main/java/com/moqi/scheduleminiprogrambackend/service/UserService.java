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

    JSONObject certification(String skey,String name,String studentId,String avatar);

    ResultVO<UserVO> getInformation(String skey);

    /*JSONObject updataInformation()*/
}
