package com.moqi.scheduleminiprogrambackend.service;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.vo.MessageZoneVO;

import java.util.List;

public interface MessageService {

    /**
     * 学生创建一个新的留言区域
     * @param skey 登录凭证
     * @param topic 留言区域的主题
     * @return 规定返回结构
     */
    JSONObject createMessageZone(String skey,String topic);

    /**
     * 老师查看留言区的列表
     * @param pageIndex 页数
     * @param pageSize 每页大小
     * @return 规定返回结构
     */
    JSONObject getMessageZone(int pageIndex,int pageSize);

    /**
     * 学生查看自己的留言区
     * @param skey 学生登陆凭证
     * @return 规定规范结构
     */
    JSONObject getMessageZoneBySkey(String skey);

    /**
     * 创建留言
     * @param skey 登录凭证
     * @param content 内容
     * @param zoneId 留言区的id
     * @return 规定的返回结构
     */
    JSONObject createMessage(String skey,String content,int zoneId);

    /**
     * 查看留言
     * @param skey 登陆凭证
     * @param zoneId 留言区id
     * @return 规定的返回结构
     */
    JSONObject getMessageByZoneId(String skey,int zoneId);


    /**
     * 根据用户的id获取用户的留言信息
     * @param userId 用户id
     * @return 留言区列表
     */
    public List<MessageZoneVO> getMessageZoneByUserId(int userId);
}
