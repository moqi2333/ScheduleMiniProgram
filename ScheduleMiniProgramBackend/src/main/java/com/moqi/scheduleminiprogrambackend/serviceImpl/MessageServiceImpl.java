package com.moqi.scheduleminiprogrambackend.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.mapperService.MessageMapper;
import com.moqi.scheduleminiprogrambackend.mapperService.MessageZoneMapper;
import com.moqi.scheduleminiprogrambackend.mapperService.UserMapper;
import com.moqi.scheduleminiprogrambackend.po.Appointment;
import com.moqi.scheduleminiprogrambackend.po.Message;
import com.moqi.scheduleminiprogrambackend.po.MessageZone;
import com.moqi.scheduleminiprogrambackend.po.User;
import com.moqi.scheduleminiprogrambackend.service.MessageService;
import com.moqi.scheduleminiprogrambackend.service.UserService;
import com.moqi.scheduleminiprogrambackend.util.Constant;
import com.moqi.scheduleminiprogrambackend.util.ResponseUtil;
import com.moqi.scheduleminiprogrambackend.vo.AppointmentVO;
import com.moqi.scheduleminiprogrambackend.vo.MessageVO;
import com.moqi.scheduleminiprogrambackend.vo.MessageZoneVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;


@Service
public class MessageServiceImpl implements MessageService {

    @Resource
    UserMapper userMapper;

    @Resource
    MessageMapper messageMapper;

    @Resource
    MessageZoneMapper messageZoneMapper;

    private static final int TEACHER_TO_STUDENT=1;

    private static final int STUDENT_TO_TEACHER=2;

    private static final String TEACHER_NAME="毕菲菲";



    /**
     * 学生创建一个新的留言区域
     * @param skey 登录凭证
     * @param topic 留言区域的主题
     * @return 规定返回结构
     */
    @Override
    public JSONObject createMessageZone(String skey,String topic) {
        HashMap<String,Object> res;
        if(skey==null||topic==null||topic.length()==0){
            res=ResponseUtil.createResponse(Constant.FAIL,"skey错误或未填写主题，请重新填写");
            return new JSONObject(res);
        }
        User student=userMapper.selectBySkey(skey);
        if(student==null){
            res= ResponseUtil.createResponse(Constant.FAIL,"未根据skey查询到相关用户");
            return new JSONObject(res);
        }
        MessageZone messageZone=new MessageZone(student.getOpenId(),topic,new Date());
        if(messageZoneMapper.insert(messageZone)>0){
            res=ResponseUtil.createResponse(Constant.SUCCESS,"创建成功");
        }else {
            res=ResponseUtil.createResponse(Constant.FAIL,"创建失败，数据库发生未知错误");
        }
        return new JSONObject(res);
    }

    /**
     * 老师查看留言区的列表
     * @param pageIndex 页数
     * @param pageSize  每页大小
     * @return 规定返回结构
     */
    @Override
    public JSONObject getMessageZone(int pageIndex, int pageSize) {
        HashMap<String,Object> res;
        List<MessageZone> messageZoneList=messageZoneMapper.getAllMessageZone();
        //按时间倒序排序
        messageZoneList.sort(Comparator.comparing(MessageZone::getCreateTime,Comparator.reverseOrder()));

        int length=messageZoneList.size();
        int start=(pageIndex-1)*pageSize;
        int end=pageIndex*pageSize;
        List<MessageZoneVO> messageZoneVOList=new ArrayList<>();
        for(int i=start;i<end&&i<length;i++){
            MessageZone messageZone=messageZoneList.get(i);
            User user=userMapper.selectByOpenId(messageZone.getStudentOpenId());
            int newMessageNum=messageMapper.countNewMessage(messageZone.getMessageZoneId(),STUDENT_TO_TEACHER);
            MessageZoneVO messageZoneVO=new MessageZoneVO(messageZone,user,newMessageNum);
            messageZoneVOList.add(messageZoneVO);
        }
        res=ResponseUtil.createResponse(Constant.SUCCESS,"查询成功");
        res.put("list",messageZoneVOList);
        return new JSONObject(res);
    }

    /**
     * 学生查看自己的留言区
     * @param skey 学生登陆凭证
     * @return 规定规范结构
     */
    @Override
    public JSONObject getMessageZoneBySkey(String skey) {
        HashMap<String,Object> res;
        User student=userMapper.selectBySkey(skey);
        if(student==null){
            res= ResponseUtil.createResponse(Constant.FAIL,"未根据skey查询到相关用户");
            return new JSONObject(res);
        }
        List<MessageZone> messageZoneList=messageZoneMapper.getMessageZoneByOpenId(student.getOpenId());
        //按时间倒序排序
        messageZoneList.sort(Comparator.comparing(MessageZone::getCreateTime,Comparator.reverseOrder()));

        List<MessageZoneVO> messageZoneVOList=new ArrayList<>();
        for (MessageZone messageZone : messageZoneList) {
            int newMessageNum = messageMapper.countNewMessage(messageZone.getMessageZoneId(), TEACHER_TO_STUDENT);
            MessageZoneVO messageZoneVO = new MessageZoneVO(messageZone, student, newMessageNum);
            messageZoneVOList.add(messageZoneVO);
        }
        res=ResponseUtil.createResponse(Constant.SUCCESS,"查询成功");
        res.put("list",messageZoneVOList);
        return new JSONObject(res);
    }

    /**
     * 创建留言
     * @param skey    登录凭证
     * @param content 内容
     * @param zoneId  留言区的id
     * @return 规定的返回结构
     */
    @Override
    public JSONObject createMessage(String skey, String content, int zoneId) {
        HashMap<String,Object> res;
        if(content==null||content.length()==0){
            res=ResponseUtil.createResponse(Constant.FAIL,"未正确填写留言内容，请重新填写");
            return new JSONObject(res);
        }
        User user=userMapper.selectBySkey(skey);
        if(user==null){
            res= ResponseUtil.createResponse(Constant.FAIL,"未根据skey查询到相关用户");
            return new JSONObject(res);
        }

        Message message;
        if(user.getIsTeacher()==1){
            message=new Message(TEACHER_TO_STUDENT,content,new Date(),zoneId,0);
        }
        else {
            message=new Message(STUDENT_TO_TEACHER,content,new Date(),zoneId,0);
        }

        if(messageMapper.insert(message)>0){
            res=ResponseUtil.createResponse(Constant.SUCCESS,"创建成功");
        }
        else{
            res=ResponseUtil.createResponse(Constant.FAIL,"创建失败，数据库发生未知错误");
        }
        return new JSONObject(res);
    }

    /**
     * 查看留言
     * @param skey   登陆凭证
     * @param zoneId 留言区id
     * @return 规定的返回结构
     */
    @Override
    public JSONObject getMessageByZoneId(String skey, int zoneId) {
        HashMap<String,Object> res;
        MessageZone messageZone=messageZoneMapper.getMessageZoneById(zoneId);
        User user=userMapper.selectBySkey(skey);
        if(user==null){
            res= ResponseUtil.createResponse(Constant.FAIL,"未根据skey查询到相关用户");
            return new JSONObject(res);
        }
        User teacher=userMapper.getTeacher(TEACHER_NAME);
        User student=userMapper.selectByOpenId(messageZone.getStudentOpenId());
        /*if(!user.getOpenId().equals(teacher.getOpenId())&&!user.getOpenId().equals(student.getOpenId())){
            res= ResponseUtil.createResponse(Constant.FAIL,"该学生用户和此留言学生用户不同");
            return new JSONObject(res);
        }*/

        List<Message> messageList=messageMapper.getMessageByZoneId(zoneId);
        //按时间顺序排序
        messageList.sort(Comparator.comparing(Message::getCreateTime));

        List<MessageVO> messageVOList=new ArrayList<>();
        for (Message message : messageList) {
            messageVOList.add(new MessageVO(message));
        }

        res=ResponseUtil.createResponse(Constant.SUCCESS,"查询成功");
        res.put("teacherAvatar",teacher.getProfileUrl());
        res.put("studentAvatar",student.getProfileUrl());
        res.put("studentId",student.getStudentId());
        res.put("studentName",student.getName());
        res.put("topic",messageZone.getTopic());
        res.put("list",messageVOList);

        if(user.getIsTeacher()==1){
            messageMapper.updateStatusByZoneId(zoneId,STUDENT_TO_TEACHER);
        }
        else{
            messageMapper.updateStatusByZoneId(zoneId,TEACHER_TO_STUDENT);
        }
        return new JSONObject(res);
    }

    /**
     * 根据用户的id获取用户的留言信息
     * @param userId 用户id
     * @return 留言区列表
     */
    @Override
    public List<MessageZoneVO> getMessageZoneByUserId(int userId) {
        User user=userMapper.getStudentByUserId(userId);
        List<MessageZone> messageZoneList=messageZoneMapper.getMessageZoneByOpenId(user.getOpenId());
        messageZoneList.sort(Comparator.comparing(MessageZone::getCreateTime,Comparator.reverseOrder()));

        List<MessageZoneVO> messageZoneVOList=new ArrayList<>();
        for (MessageZone messageZone : messageZoneList) {
            messageZoneVOList.add(new MessageZoneVO(messageZone,user,0));
        }
        return messageZoneVOList;
    }
}
