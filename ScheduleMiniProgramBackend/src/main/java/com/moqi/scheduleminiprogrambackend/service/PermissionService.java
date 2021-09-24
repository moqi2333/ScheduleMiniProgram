package com.moqi.scheduleminiprogrambackend.service;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.vo.PermissionVO;

public interface PermissionService {

    /**
     * 根据skey获取当前下发消息的权限数量
     * @param skey 用户的登录凭证
     * @return 含有当前用户权限信息的数据集
     */
    PermissionVO getPermission(String skey);

    /**
     * 增加用户可收到预约通知的数量
     * @param skey 用户的登录凭证
     * @return 规定格式返回值
     */
    JSONObject increaseAppointmentCount(String skey);

    /**
     * 增加用户可收到状态变更通知的数量
     * @param skey 用户的登录凭证
     * @return 规定格式返回值
     */
    JSONObject increaseStatusCount(String skey);

    /**
     * 增加用户可收到取消通知的数量
     * @param skey 用户的登录凭证
     * @return 规定格式返回值
     */
    JSONObject increaseCancelCount(String skey);


    /**
     * 增加用户可收到留言通知的数量
     * @param skey 登陆凭证
     * @return 规定的返回格式
     */
    JSONObject increaseMessageCount(String skey);
}
