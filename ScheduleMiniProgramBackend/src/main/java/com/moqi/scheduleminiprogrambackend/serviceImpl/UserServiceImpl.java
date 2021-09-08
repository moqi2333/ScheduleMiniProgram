package com.moqi.scheduleminiprogrambackend.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.mapperService.PermissionMapper;
import com.moqi.scheduleminiprogrambackend.mapperService.UserMapper;
import com.moqi.scheduleminiprogrambackend.po.User;
import com.moqi.scheduleminiprogrambackend.service.UserService;
import com.moqi.scheduleminiprogrambackend.util.Constant;
import com.moqi.scheduleminiprogrambackend.util.ResponseUtil;
import com.moqi.scheduleminiprogrambackend.util.WeChatUtil;
import com.moqi.scheduleminiprogrambackend.vo.ResultVO;
import com.moqi.scheduleminiprogrambackend.vo.UserVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserMapper userMapper;
    @Resource
    PermissionMapper permissionMapper;

    /**
     * 用于小程序的登录
     * @param code 前端传入的code
     * @return 请求的返回体
     */
    @Override
    public JSONObject login(String code) {
        //从微信服务器获取openId和sessionKey
        JSONObject sessionKeyOpenId = WeChatUtil.getSessionByCode(code);

        //System.out.println(sessionKeyOpenId);
        String openid = sessionKeyOpenId.getString("openid");
        String sessionKey = sessionKeyOpenId.getString("session_key");

        //判断是否拿到正确的openId
        if(openid==null){
            HashMap<String,Object> res= ResponseUtil.createResponse(Constant.FAIL,"code错误");
            return new JSONObject(res);
        }
        //根据返回的User实体类，判断用户是否是新用户，是的话，将用户信息存到数据库；不是的话，更新最新登录时间
        User user=userMapper.selectByOpenId(openid);

        // uuid生成唯一key，用于维护微信小程序用户与服务端的会话
        String skey = UUID.randomUUID().toString();
        if(user==null){
            user = new User();
            user.setOpenId(openid);
            user.setSkey(skey);
            user.setCreateTime(new Date());
            user.setLastVisitTime(new Date());
            user.setSessionKey(sessionKey);

            userMapper.insert(user);
            //插入权限信息
            permissionMapper.insert(user.getOpenId());


            //编写返回信息
            HashMap<String, Object> res= ResponseUtil.createResponse(Constant.SUCCESS,"新用户");
            res.put("skey",skey);
            System.out.println("新用户"+user.getOpenId()+"登录");
            return new JSONObject(res);
        }
        else {

            // 已存在，更新用户登录时间
            user.setLastVisitTime(new Date());
            //更新sessionKey
            user.setSessionKey(sessionKey);
            // 重新设置会话skey
            user.setSkey(skey);

            userMapper.updateById(user);

            //编写返回信息
            HashMap<String, Object> res= ResponseUtil.createResponse(Constant.SUCCESS,"老用户");
            res.put("skey",skey);
            System.out.println("老用户"+user.getOpenId()+"登录");
            return new JSONObject(res);
        }
    }

    @Override
    public JSONObject certification(String skey, String name, String studentId,String avatar) {
        HashMap<String, Object> res;
        //判断合法性
        if(name==null||name.length()==0){
            res=ResponseUtil.createResponse(
                    Constant.FAIL,"未填写姓名或填写不正确，请重新填写后提交");
            return new JSONObject(res);
        }
        if(studentId==null||studentId.length()==0){
            res=ResponseUtil.createResponse(
                    Constant.FAIL,"未填写学号或填写不正确，请重新填写后提交");
            return new JSONObject(res);
        }

        User user=userMapper.selectBySkey(skey);
        if(user==null){
            res=ResponseUtil.createResponse(Constant.FAIL,"skey错误，尚无此用户");
            return new JSONObject(res);
        }
        user.setName(name);
        user.setStudentId(studentId);
        if(avatar!=null){
            user.setProfileUrl(avatar);
        }
        userMapper.updateInfoById(user);
        res=ResponseUtil.createResponse(Constant.SUCCESS,"信息完善成功");
        return new JSONObject(res);

    }

    @Override
    public ResultVO<UserVO> getInformation(String skey) {
        User user=userMapper.selectBySkey(skey);
        if(user==null){
            return new ResultVO<>(Constant.FAIL,"skey错误，尚无此用户");
        }
        return new ResultVO<>(Constant.SUCCESS,"获取成功",new UserVO(user));
    }
}
