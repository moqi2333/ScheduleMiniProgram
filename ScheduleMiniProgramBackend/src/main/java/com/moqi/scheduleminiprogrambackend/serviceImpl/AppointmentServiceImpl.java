package com.moqi.scheduleminiprogrambackend.serviceImpl;

import cn.hutool.core.date.DateTime;
import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.mapperService.AppointmentMapper;
import com.moqi.scheduleminiprogrambackend.mapperService.PermissionMapper;
import com.moqi.scheduleminiprogrambackend.mapperService.UserMapper;
import com.moqi.scheduleminiprogrambackend.po.Appointment;
import com.moqi.scheduleminiprogrambackend.po.User;
import com.moqi.scheduleminiprogrambackend.service.AppointmentService;
import com.moqi.scheduleminiprogrambackend.util.*;
import com.moqi.scheduleminiprogrambackend.vo.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.*;

/**
 * @author moqi
 */
@Service
public class AppointmentServiceImpl implements AppointmentService {


    @Resource
    AppointmentMapper appointmentMapper;

    @Resource
    UserMapper userMapper;

    @Resource
    PermissionMapper permissionMapper;

    @Resource
    OssClientUtil ossClientUtil;

    static final String TEACHER_NAME="毕菲菲";

    /**
     * 学生创建一个预约
     *
     * @param appointmentInfo 有关本次预约的相关信息
     * @return 本次请求的返回体
     */
    @Override
    public JSONObject create(Map<String, String> appointmentInfo) {
        HashMap<String,Object> res;
        String skey=appointmentInfo.get("skey");
        User user =userMapper.selectBySkey(skey);
        if(user==null){
            res= ResponseUtil.createResponse(Constant.FAIL,"用户信息错误");
            return new JSONObject(res);
        }
        String dateString=appointmentInfo.get("date");
        String startTimeString=appointmentInfo.get("startTime");
        String endTimeString=appointmentInfo.get("endTime");
        //对是否填写时间进行判断
        if(startTimeString==null||endTimeString==null||startTimeString.length()!=5||endTimeString.length()!=5){
            res=ResponseUtil.createResponse(Constant.FAIL,"未填写时间,请正确填写时间段");
            return new JSONObject(res);
        }
        //解析日期
        Date date=Date.valueOf(dateString);
        //解析时间
        Time startTime=Time.valueOf(startTimeString+":00");
        Time endTime=Time.valueOf(endTimeString+":00");
        if(startTime.after(endTime)){
            res=ResponseUtil.createResponse(Constant.FAIL,"开始时间不能晚于结束时间");
            return new JSONObject(res);
        }
        //判断预约时间的合理性
        Timestamp appointmentTime=Timestamp.valueOf(date.toString()+" "+ startTime);
        Timestamp timeNow=new Timestamp(System.currentTimeMillis());
        long timeDiff=appointmentTime.getTime()-timeNow.getTime();
        if(timeDiff<=Constant.THREE_HOURS){
            res=ResponseUtil.createResponse(Constant.FAIL,"预约失败，预约需要至少提前三小时");
            if(timeDiff<=0){
                res=ResponseUtil.createResponse(Constant.FAIL,"预约时间必须在当前时间之后");
            }
            return new JSONObject(res);
        }

        String place=appointmentInfo.get("place");
        String content=appointmentInfo.get("content");
        String other=appointmentInfo.get("other");
        if(place==null){
            res=ResponseUtil.createResponse(Constant.FAIL,"未填写地点,请正确填写地点");
            return new JSONObject(res);
        }
        if(content==null){
            res=ResponseUtil.createResponse(Constant.FAIL,"未填写预约相关内容,请正确填写相关内容");
            return new JSONObject(res);
        }
        Appointment appointment=new Appointment(date,startTime,endTime,place,content,other,user.getOpenId());

        if(appointmentMapper.insert(appointment)>0){
            int appointmentId=appointmentMapper.getLatestId();
            res=ResponseUtil.createResponse(Constant.SUCCESS,"创建成功");
            res.put("appointmentId",appointmentId);

            //给老师发送消息
            User teacher=userMapper.getTeacher(TEACHER_NAME);
            if("ok".equals(WeChatUtil.sendAppointmentMsg(teacher.getOpenId(),user.getName(),place,dateString+" "+startTimeString,
                    dateString+" "+endTimeString,content))){
                permissionMapper.updateAppointmentCount(teacher.getOpenId(),"-1");
            }
        }else {
            res=ResponseUtil.createResponse(Constant.FAIL,"创建失败");
        }
        return new JSONObject(res);
    }

    /**
     * 学生取消预约
     *
     * @param skey          学生的登录凭证，用于验证
     * @param appointmentId 需要取消的预约Id
     * @return 取消结果
     */
    @Override
    public JSONObject cancel(String skey, int appointmentId) {
        HashMap<String,Object> res;
        Appointment appointment=appointmentMapper.getAppointmentById(appointmentId);
        User user=userMapper.selectBySkey(skey);
        if(appointment==null||user==null){
            res=ResponseUtil.createResponse(
                    Constant.FAIL,"skey或appointmentId错误，未查询到相应数据");
            return new JSONObject(res);
        }
        //skey和预约者不是同一个人
        if(!user.getOpenId().equals(appointment.getStudentOpenId())){
            res=ResponseUtil.createResponse(Constant.FAIL,"预约人和当前用户信息不匹配");
            return new JSONObject(res);
        }
        if(appointment.getStatus()==-1){
            res=ResponseUtil.createResponse(Constant.FAIL,"该预约已经被拒绝");
            return new JSONObject(res);
        }
        //判断当前时间是否快到预约时间，快到的话，就无法取消
        Date appointmentDate=appointment.getDate();
        Timestamp appointmentTime=Timestamp.valueOf(appointmentDate.toString()+" "+appointment.getStartTime().toString());
        Timestamp timeNow=new Timestamp(System.currentTimeMillis());
        if((appointmentTime.getTime()-timeNow.getTime())<=Constant.THREE_HOURS){
            res=ResponseUtil.createResponse(Constant.FAIL,"预约即将开始或已经结束，不可取消");
            return new JSONObject(res);
        }

        if(appointmentMapper.delete(appointmentId)>0){
            res=ResponseUtil.createResponse(Constant.SUCCESS,"取消成功");
            //提醒老师
            User teacher=userMapper.getTeacher(TEACHER_NAME);
            //reason暂时没有实现，选择为null，未来可添加
            if("ok".equals(WeChatUtil.sendCancelMsg(teacher.getOpenId(),user.getName(),appointment.getPlace(),appointmentDate.toString(),
                    appointment.getStartTime().toString().substring(0,5),appointment.getEndTime().toString().substring(0,5),null))){
                permissionMapper.updateCancelCount(teacher.getOpenId(),"-1");
            }

        }else {
            res=ResponseUtil.createResponse(Constant.FAIL,"取消失败，数据库发生未知错误");
        }
        return new JSONObject(res);
    }

    /**
     * 根据日期获取预约信息，将学生和老师分开处理
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @param skey      登录凭证
     * @return 定义的返回结构
     */
    @Override
    public JSONObject getAppointmentByDate(Date startDate, Date endDate, String skey) {
        HashMap<String,Object> res;
        //此行用于适配老版本，未来可删除
        if(skey==null){
            res=teacherGetAppointmentByDate(startDate,endDate);
            return new JSONObject(res);
        }

        User user=userMapper.selectBySkey(skey);
        if(user==null){
            res=ResponseUtil.createResponse(Constant.FAIL,"未根据skey查询到相关用户");
            return new JSONObject(res);
        }
        if(user.getIsTeacher()!=0){
            res=teacherGetAppointmentByDate(startDate,endDate);
        }
        else {
            res=studentGetAppointmentByDate(startDate,endDate,user.getOpenId());
        }
        return new JSONObject(res);
    }


    private HashMap<String,Object> teacherGetAppointmentByDate(Date startDate, Date endDate) {
        HashMap<String,Object> res;
        List<Appointment> appointmentList= appointmentMapper.getAppointmentByDate(startDate,endDate);
        if(appointmentList==null){
            res=ResponseUtil.createResponse(Constant.FAIL,"数据库查询出错");
            return res;
        }
        //先排序
        appointmentList.sort(Comparator.comparing(Appointment::getDate));


        //构造返回值需要的结构
        int listIndex=0;
        int jsonIndex=1;
        int length=appointmentList.size();
        Date dateTmp=new Date(startDate.getTime());
        ArrayList<AppointmentVO> appointmentVOArrayList=new ArrayList<>();
        res=new HashMap<>();

        while(!dateTmp.after(endDate)){
            //双指针实现，当前日期指针和当前列表指针的日期相同，就将元素插入列表中，并移动列表指针
            if(listIndex<length&& appointmentList.get(listIndex).getDate().equals(dateTmp)){
                String studentOpenId=appointmentList.get(listIndex).getStudentOpenId();
                User user=userMapper.selectByOpenId(studentOpenId);
                //构造VO
                AppointmentVO appointmentVO=new AppointmentVO(appointmentList.get(listIndex),user,"true");

                appointmentVOArrayList.add(appointmentVO);
                listIndex++;
            }
            //不相同就将列表加入返回体，移动日期指针,并清空列表
            else {
                dateTmp= DateUtil.getNextDay(dateTmp);
                res.put(String.valueOf(jsonIndex),appointmentVOArrayList);
                appointmentVOArrayList=new ArrayList<>();
                jsonIndex++;
            }
        }
        return res;
    }

    private HashMap<String,Object> studentGetAppointmentByDate(Date startDate, Date endDate,String openId){
        HashMap<String,Object> res;

        List<Appointment> appointmentList= appointmentMapper.getAppointmentByDate(startDate,endDate);
        if(appointmentList==null){
            res=ResponseUtil.createResponse(Constant.FAIL,"数据库查询出错");
            return res;
        }
        //先排序
        appointmentList.sort(Comparator.comparing(Appointment::getDate));


        //构造返回值需要的结构
        int listIndex=0;
        int jsonIndex=1;
        int length=appointmentList.size();
        Date dateTmp=new Date(startDate.getTime());
        ArrayList<AppointmentVO> appointmentVOArrayList=new ArrayList<>();
        res=new HashMap<>();

        while(!dateTmp.after(endDate)){
            //双指针实现，当前日期指针和当前列表指针的日期相同，就将元素插入列表中，并移动列表指针
            if(listIndex<length&& appointmentList.get(listIndex).getDate().equals(dateTmp)){
                String studentOpenId=appointmentList.get(listIndex).getStudentOpenId();
                User user=userMapper.selectByOpenId(studentOpenId);
                //构造VO
                AppointmentVO appointmentVO=new AppointmentVO(appointmentList.get(listIndex),user,
                        studentOpenId.equals(openId)?"true":"false");

                appointmentVOArrayList.add(appointmentVO);
                listIndex++;
            }
            //不相同就将列表加入返回体，移动日期指针,并清空列表
            else {
                dateTmp= DateUtil.getNextDay(dateTmp);
                res.put(String.valueOf(jsonIndex),appointmentVOArrayList);
                appointmentVOArrayList=new ArrayList<>();
                jsonIndex++;
            }
        }
        return res;
    }

    /**
     * 填写或更新预约记录
     *
     * @param appointmentId 预约Id
     * @param record        预约记录内容
     * @return 请求结果
     */
    @Override
    public JSONObject updateRecord(int appointmentId, String record) {
        HashMap<String,Object> res;
        Appointment appointment=appointmentMapper.getAppointmentById(appointmentId);
        if(appointment==null){
            res=ResponseUtil.createResponse(Constant.FAIL,"appointmentId错误，未查询到相关预约信息");
            return new JSONObject(res);
        }
        boolean isNull=appointment.getRecord()==null;
        DateTime dateNow=new DateTime();
        if(appointmentMapper.updateRecord(appointmentId,record,dateNow)>0){
            res=ResponseUtil.createResponse(Constant.SUCCESS,
                    isNull?"填写成功":"修改成功");
        }
        else{
            res=ResponseUtil.createResponse(Constant.FAIL,"数据库更新失败");
        }
        return new JSONObject(res);
    }

    /**
     * 查询谈话记录的列表，以分页形式返回
     * @param pageIndex 页数
     * @param pageSize 每页的数量
     * @param status  表示记录是否填写
     * @return 规定的返回结构
     */
    @Override
    public JSONObject getRecordList(int pageIndex, int pageSize,int status) {
        List<Appointment> appointmentList;
        HashMap<String,Object> res=new HashMap<>();
        //按所需状态进行查询
        if(status==0){
            //获取未填写记录的列表
            appointmentList=appointmentMapper.getAllByRecordStatus("");
        }
        else if(status==1){
            //获取已经填写记录的列表
            appointmentList=appointmentMapper.getAllByRecordStatus("not");
        }
        else {
            res=ResponseUtil.createResponse(Constant.FAIL,"状态码错误，查询失败");
            return new JSONObject(res);
        }

        //将列表按时间倒序
        appointmentList.sort(Comparator.comparing(Appointment::getDate));
        Collections.reverse(appointmentList);

        int length=appointmentList.size();
        int start=(pageIndex-1)*pageSize;
        int end=pageIndex*pageSize;
        List<RecordListItemVO> recordListItemVOList =new ArrayList<>();
        for(int i=start;i<end&&i<length;i++){
            Appointment appointment=appointmentList.get(i);
            User user=userMapper.selectByOpenId(appointment.getStudentOpenId());
            RecordListItemVO recordListItemVO =new RecordListItemVO(appointment);
            recordListItemVO.setName(user.getName());
            recordListItemVO.setStudentId(user.getStudentId());
            recordListItemVO.setAvatar(user.getProfileUrl());
            recordListItemVOList.add(recordListItemVO);
        }
        res.put("totalSize",length);
        res.put("recordList", recordListItemVOList);
        return new JSONObject(res);
    }

    /**
     * 获取单个谈话记录的信息
     *
     * @param appointmentId 预约Id
     * @return 谈话记录信息
     */
    @Override
    public RecordVO getRecord(int appointmentId) {
        Appointment appointment=appointmentMapper.getAppointmentById(appointmentId);
        User user=userMapper.selectByOpenId(appointment.getStudentOpenId());
        RecordVO recordVO=new RecordVO(appointment);
        recordVO.setName(user.getName());
        recordVO.setAvatar(user.getProfileUrl());
        recordVO.setStudentId(user.getStudentId());
        return recordVO;
    }

    /**
     * 学生填写或更新反馈
     * @param feedbackInfo 反馈相关的信息
     * @return 请求结果
     */
    @Override
    public JSONObject updateFeedback(Map<String, String> feedbackInfo) {
        HashMap<String,Object> res;
        String skey=feedbackInfo.get("skey");
        int appointmentId=Integer.parseInt(feedbackInfo.get("appointmentId"));
        String feedback=feedbackInfo.get("feedback");

        User user=userMapper.selectBySkey(skey);
        Appointment appointment=appointmentMapper.getAppointmentById(appointmentId);
        if(appointment==null||user==null){
            res=ResponseUtil.createResponse(Constant.FAIL,"skey或appointmentId错误，未查询到相关数据");
            return new JSONObject(res);
        }
        if(!user.getOpenId().equals(appointment.getStudentOpenId())){
            res=ResponseUtil.createResponse(Constant.FAIL,"当前用户和预约人不匹配");
            return new JSONObject(res);
        }
        boolean isNull=appointment.getFeedback()==null;
        DateTime dateNow=new DateTime();
        if(appointmentMapper.updateFeedback(appointmentId,feedback,dateNow)>0){
            res=ResponseUtil.createResponse(Constant.SUCCESS,
                    isNull?"填写成功":"修改成功");
        }else {
            res=ResponseUtil.createResponse(Constant.FAIL,"数据库更新失败");
        }
        return new JSONObject(res);
    }

    /**
     * 查看反馈的列表，以分页形式返回
     * @param pageIndex 页数
     * @param pageSize 每页的数量
     * @param status  表示反馈是否已经填写
     * @return 规定的返回结构
     */
    @Override
    public JSONObject getFeedbackList(int pageIndex, int pageSize,int status) {
        List<Appointment> appointmentList;
        HashMap<String,Object> res;
        //按所需状态进行查询
        if(status==0){
            appointmentList=appointmentMapper.getAllByFeedbackStatus("");
        }
        else if(status==1){
            appointmentList=appointmentMapper.getAllByFeedbackStatus("not");
        }
        else {
            res=ResponseUtil.createResponse(Constant.FAIL,"状态码错误，查询失败");
            return new JSONObject(res);
        }
        //将列表按时间倒序
        appointmentList.sort(Comparator.comparing(Appointment::getDate));
        Collections.reverse(appointmentList);

        int length=appointmentList.size();
        int start=(pageIndex-1)*pageSize;
        int end=pageIndex*pageSize;
        List<FeedbackListItemVO> feedbackListItemVOList =new ArrayList<>();
        for(int i=start;i<end&&i<length;i++){
            Appointment appointment=appointmentList.get(i);
            User user=userMapper.selectByOpenId(appointment.getStudentOpenId());
            FeedbackListItemVO feedbackListItemVO=new FeedbackListItemVO(appointment);
            feedbackListItemVO.setName(user.getName());
            feedbackListItemVO.setStudentId(user.getStudentId());
            feedbackListItemVO.setAvatar(user.getProfileUrl());
            feedbackListItemVOList.add(feedbackListItemVO);
        }
        res=new HashMap<>();
        res.put("totalSize",length);
        res.put("recordList", feedbackListItemVOList);
        return new JSONObject(res);
    }

    /**
     * 获取单个学生反馈
     *
     * @param appointmentId 预约Id
     * @return 反馈的相关信息
     */
    @Override
    public FeedbackVO getFeedback(int appointmentId) {
        Appointment appointment=appointmentMapper.getAppointmentById(appointmentId);
        User user=userMapper.selectByOpenId(appointment.getStudentOpenId());
        FeedbackVO feedbackVO=new FeedbackVO(appointment);
        feedbackVO.setName(user.getName());
        feedbackVO.setAvatar(user.getProfileUrl());
        feedbackVO.setStudentId(user.getStudentId());
        return feedbackVO;
    }

    /**
     * 学生修改预约时间
     *
     * @param timeInfo 预约时间的相关信息
     * @return 请求结果
     */
    @Override
    public JSONObject updateTime(Map<String, String> timeInfo) {
        HashMap<String,Object> res;
        String skey=timeInfo.get("skey");
        int appointmentId=Integer.parseInt(timeInfo.get("appointmentId"));
        //解析时间
        Time startTime=Time.valueOf(timeInfo.get("startTime")+":00");
        Time endTime=Time.valueOf(timeInfo.get("endTime")+":00");
        if(startTime.after(endTime)){
            res=ResponseUtil.createResponse(Constant.FAIL,"开始时间不能大于结束时间");
            return new JSONObject(res);
        }
        User user=userMapper.selectBySkey(skey);
        Appointment appointment=appointmentMapper.getAppointmentById(appointmentId);
        if(appointment==null||user==null){
            res=ResponseUtil.createResponse(Constant.FAIL, "skey或appointmentId错误，未查询到相关数据");
            return new JSONObject(res);
        }
        if(!user.getOpenId().equals(appointment.getStudentOpenId())){
            res=ResponseUtil.createResponse(Constant.FAIL,"当前用户和预约人不匹配");
            return new JSONObject(res);
        }
        if(appointment.getStatus()==-1){
            res=ResponseUtil.createResponse(Constant.FAIL,"该预约已经被拒绝");
            return new JSONObject(res);
        }

        //判断当前时间是否快到预约时间，快到的话，就无法取消
        Date appointmentDate=appointment.getDate();
        Timestamp appointmentTime=Timestamp.valueOf(appointmentDate.toString()+" "+appointment.getStartTime().toString());
        Timestamp timeNow=new Timestamp(System.currentTimeMillis());
        if((appointmentTime.getTime()-timeNow.getTime())<=Constant.THREE_HOURS){
            res=ResponseUtil.createResponse(Constant.FAIL,"预约即将开始或已经结束，不可修改");
            return new JSONObject(res);
        }

        appointment.setStartTime(startTime);
        appointment.setEndTime(endTime);
        if(appointmentMapper.updateTime(appointment)>0){
            res=ResponseUtil.createResponse(Constant.SUCCESS,"时间修改成功");
            //todo 提醒
        }else {
            res=ResponseUtil.createResponse(Constant.FAIL,"数据库更新错误，修改失败");
        }
        return new JSONObject(res);
    }

    /**
     * 根据状态分页返回预约列表
     *
     * @param status    是否同意的状态
     * @param pageIndex 页数
     * @param pageSize  每页大小
     * @return 自定义返回结构
     */
    @Override
    public JSONObject getAppointmentByStatus(int status, int pageIndex, int pageSize) {
        HashMap<String,Object> res;
        if(status!=0&&status!=1&&status!=-1){
            res=ResponseUtil.createResponse(Constant.FAIL,"输入的状态错误，状态只能为0，1，-1");
            return new JSONObject(res);
        }
        List<Appointment> appointmentList= appointmentMapper.getAppointmentByStatus(status);
        //按时间倒序排列
        appointmentList.sort(Comparator.comparing(Appointment::getDate));
        Collections.reverse(appointmentList);

        int length=appointmentList.size();
        int start=(pageIndex-1)*pageSize;
        int end=pageIndex*pageSize;
        List<AppointmentVO> appointmentVOList=new ArrayList<>();
        for(int i=start;i<end&&i<length;i++){
            Appointment appointment=appointmentList.get(i);
            User user=userMapper.selectByOpenId(appointment.getStudentOpenId());
            AppointmentVO appointmentVO=new AppointmentVO(appointment);
            appointmentVO.setName(user.getName());
            appointmentVO.setStudentId(user.getStudentId());
            appointmentVO.setAvatar(user.getProfileUrl());
            appointmentVOList.add(appointmentVO);
        }
        res=new HashMap<>();
        res.put("totalSize",length);
        res.put("appointmentList",appointmentVOList);
        return new JSONObject(res);
    }


    /**
     * 更新预约的状态，同意或是拒绝
     *
     * @param appointmentId 预约Id
     * @param status        预约状态，1或-1，1为接受，-1为拒绝
     * @return 状态更新结果
     */
    @Override
    public JSONObject updateStatus(int appointmentId, int status) {
        HashMap<String,Object> res;
        if(status!=1&&status!=-1){
            res=ResponseUtil.createResponse(Constant.FAIL,"状态码错误");
            return new JSONObject(res);
        }
        if(appointmentMapper.updateStatus(appointmentId,status)>0){
            res=ResponseUtil.createResponse(Constant.SUCCESS,"更新预约状态成功");
            //通知学生
            String result=status==1?"已同意":"已拒绝";
            Appointment appointment=appointmentMapper.getAppointmentById(appointmentId);
            User user=userMapper.selectByOpenId(appointment.getStudentOpenId());
            String startTimeString=appointment.getDate().toString()+" "+appointment.getStartTime().toString().substring(0,5);
            String endTimeString=appointment.getDate().toString()+" "+appointment.getEndTime().toString().substring(0,5);
            if("ok".equals(WeChatUtil.sendStatusMsg(user.getOpenId(),user.getName(),appointment.getPlace(),startTimeString,endTimeString,result))){
                permissionMapper.updateStatusCount(user.getOpenId(),"-1");
            }
        }
        else {
            res=ResponseUtil.createResponse(Constant.FAIL,"数据库更新错误，更新失败");
        }
        return new JSONObject(res);
    }

    /**
     * 根据所需状态查询某特定学生的预约信息
     * @param skey   登录凭证
     * @param status 所需状态
     * @return 预约信息的列表
     */
    @Override
    public List<AppointmentVO> getStudentAppointmentByStatus(String skey, int status) {
        User user=userMapper.selectBySkey(skey);
        if(user==null){
            throw new RuntimeException("未根据skey查询到相关用户");
        }
        List<Appointment> appointmentList=appointmentMapper.getStudentAppointmentByStatus(user.getOpenId(),status);
        appointmentList.sort(Comparator.comparing(Appointment::getDate));
        Collections.reverse(appointmentList);

        List<AppointmentVO> appointmentVOList=new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            AppointmentVO appointmentVO=new AppointmentVO(appointment);
            appointmentVO.setName(user.getName());
            appointmentVO.setStudentId(user.getStudentId());
            appointmentVO.setAvatar(user.getProfileUrl());
            appointmentVOList.add(appointmentVO);
        }
        return appointmentVOList;
    }

    /**
     * 根据反馈是否填写查询某特定学生的反馈信息
     * @param skey   登录凭证
     * @param status 预约信息的列表
     * @return 反馈信息列表
     */
    @Override
    public List<FeedbackListItemVO> getStudentFeedbackByStatus(String skey, int status) {
        User user=userMapper.selectBySkey(skey);
        List<Appointment> appointmentList = null;
        if(status==0) {
            appointmentList = appointmentMapper.getStudentFeedbackByStatus(user.getOpenId(),"");
        }
        if(status==1){
            appointmentList = appointmentMapper.getStudentFeedbackByStatus(user.getOpenId(),"not");
        }
        assert appointmentList != null;
        appointmentList.sort(Comparator.comparing(Appointment::getDate));
        Collections.reverse(appointmentList);

        List<FeedbackListItemVO> feedbackListItemVOList=new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            FeedbackListItemVO feedbackListItemVO=new FeedbackListItemVO(appointment);
            feedbackListItemVO.setName(user.getName());
            feedbackListItemVO.setStudentId(user.getStudentId());
            feedbackListItemVO.setAvatar(user.getProfileUrl());
            feedbackListItemVOList.add(feedbackListItemVO);
        }
        return feedbackListItemVOList;
    }

    /**
     * 获取记录的表格
     * @return 规定数据格式
     */
    @Override
    public JSONObject getForm() {
        HashMap<String,Object> res;
        List<Appointment> appointmentList=appointmentMapper.getAllByRecordStatus("not");
        appointmentList.sort(Comparator.comparing(Appointment::getDate));
        List<ExcelInfo> excelInfos=new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            User user=userMapper.selectByOpenId(appointment.getStudentOpenId());
            ExcelInfo excelInfo=new ExcelInfo(appointment,user);
            excelInfos.add(excelInfo);
        }
        String fileName=new Date(System.currentTimeMillis())+".xlsx";
        ossClientUtil.init();
        ossClientUtil.uploadExcel2Oss(ExcelUtil.createExcel(excelInfos), fileName);
        String url=ossClientUtil.getUrlByName(fileName);
        res=ResponseUtil.createResponse(Constant.SUCCESS,"导出成功");
        res.put("url",url);
        return new JSONObject(res);
    }

}
