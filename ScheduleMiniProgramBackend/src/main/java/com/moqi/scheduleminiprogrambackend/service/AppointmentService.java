package com.moqi.scheduleminiprogrambackend.service;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.vo.*;

import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface AppointmentService {

    /**
     * 学生创建一个预约
     * @param appointmentInfo 有关本次预约的相关信息
     * @return 本次请求的返回体
     */
    public JSONObject create(Map<String,String> appointmentInfo);


    /**
     * 学生取消预约
     * @param skey 学生的登录凭证，用于验证
     * @param appointmentId 需要取消的预约Id
     * @return 取消结果
     */
    public JSONObject cancel(String skey,int appointmentId);


    /**
     * 根据日期获取预约信息，将学生和老师分开处理
     * @param startDate 开始日期
     * @param endDate 结束日期
     * @param skey 登录凭证
     * @return 定义的返回结构
     */
    public JSONObject getAppointmentByDate(Date startDate,Date endDate,String skey);


    /**
     * 填写或更新预约记录
     * @param appointmentId 预约Id
     * @param record 预约记录内容
     * @return 请求结果
     */
    public JSONObject updateRecord(int appointmentId,String record);


    /**
     * 查询谈话记录的列表，以分页形式返回
     * @param pageIndex 页数
     * @param pageSize 每页的数量
     * @param status  表示记录是否填写
     * @return 规定的返回结构
     */
    public JSONObject getRecordList(int pageIndex,int pageSize,int status);


    /**
     * 获取单个谈话记录的信息
     * @param appointmentId 预约Id
     * @return 谈话记录信息
     */
    public RecordVO getRecord(int appointmentId);

    /**
     * 学生填写或更新反馈
     * @param feedbackInfo 反馈相关的信息
     * @return 请求结果
     */
    public JSONObject updateFeedback(Map<String,String> feedbackInfo);


    /**
     * 查看反馈的列表，以分页形式返回
     * @param pageIndex 页数
     * @param pageSize 每页的数量
     * @param status  表示反馈是否已经填写
     * @return 规定的返回结构
     */
    public JSONObject getFeedbackList(int pageIndex,int pageSize,int status);


    /**
     * 获取单个学生反馈
     * @param appointmentId 预约Id
     * @return 反馈的相关信息
     */
    public FeedbackVO getFeedback(int appointmentId);

    /**
     * 学生修改预约时间
     * @param timeInfo 预约时间的相关信息
     * @return 请求结果
     */
    public JSONObject updateTime(Map<String,String> timeInfo);

    /**
     * 根据状态分页返回预约列表
     * @param status 是否同意的状态
     * @param pageIndex 页数
     * @param pageSize 每页大小
     * @return 自定义返回结构
     */
    public JSONObject getAppointmentByStatus(int status,int pageIndex,int pageSize);

    /**
     * 更新预约的状态，同意或是拒绝
     * @param appointmentId 预约Id
     * @param status 预约状态，1或-1，1为接受，-1为拒绝
     * @return 状态更新结果
     */
    public JSONObject updateStatus(int appointmentId,int status);


    /**
     * 根据所需状态查询某特定学生的预约信息
     * @param skey 登录凭证
     * @param status 所需状态
     * @return 预约信息的列表
     */
    public List<AppointmentVO> getStudentAppointmentByStatus(String skey,int status);

    /**
     * 根据反馈是否填写查询某特定学生的反馈信息
     * @param skey 登录凭证
     * @param status 预约信息的列表
     * @return 学生反馈信息的列表
     */
    public List<FeedbackListItemVO> getStudentFeedbackByStatus(String skey,int status);

    /**
     * 获取记录的表格
     * @param userIds 学生的编号
     * @return 规定格式
     */
    public JSONObject getForm(List<Integer> userIds);

    /**
     * 根据用户的id获取用户的预约信息
     * @param userId 用户id
     * @return 预约信息列表
     */
    public List<AppointmentVO> getAppointmentByUserId(int userId);

    ;
}
