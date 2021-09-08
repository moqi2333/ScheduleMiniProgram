package com.moqi.scheduleminiprogrambackend.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.mapperService.PermissionMapper;
import com.moqi.scheduleminiprogrambackend.mapperService.UserMapper;
import com.moqi.scheduleminiprogrambackend.po.Permission;
import com.moqi.scheduleminiprogrambackend.po.User;
import com.moqi.scheduleminiprogrambackend.service.PermissionService;
import com.moqi.scheduleminiprogrambackend.util.Constant;
import com.moqi.scheduleminiprogrambackend.util.ResponseUtil;
import com.moqi.scheduleminiprogrambackend.vo.PermissionVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;

@Service
public class PermissionServiceImpl implements PermissionService {


    @Resource
    UserMapper userMapper;

    @Resource
    PermissionMapper permissionMapper;

    /**
     * 根据skey获取当前下发消息的权限数量
     * @param skey 用户的登录凭证
     * @return 含有当前用户权限信息的数据集
     */
    @Override
    public PermissionVO getPermission(String skey) {
        User user=userMapper.selectBySkey(skey);
        if(user==null){
            throw new RuntimeException("未根据skey查询到相关用户");
        }
        String openId=user.getOpenId();
        Permission permission= permissionMapper.getPermission(openId);
        return new PermissionVO(permission);
    }

    /**
     * 增加用户可收到预约通知的数量
     * @param skey 用户的登录凭证
     * @return 规定格式返回值
     */
    @Override
    public JSONObject increaseAppointmentCount(String skey) {
        HashMap<String,Object> res;
        User user=userMapper.selectBySkey(skey);
        String openId=user.getOpenId();
        if(permissionMapper.updateAppointmentCount(openId,"+1")>0){
            Permission permission= permissionMapper.getPermission(openId);
            res= ResponseUtil.createResponse(Constant.SUCCESS,"增加成功");
            res.put("data",permission.getSendAppointmentCount());
        }
        else {
            res=ResponseUtil.createResponse(Constant.FAIL,"增加失败");
        }
        return new JSONObject(res);
    }

    /**
     * 增加用户可收到状态变更通知的数量
     * @param skey 用户的登录凭证
     * @return 规定格式返回值
     */
    @Override
    public JSONObject increaseStatusCount(String skey) {
        HashMap<String,Object> res;
        User user=userMapper.selectBySkey(skey);
        String openId=user.getOpenId();
        if(permissionMapper.updateStatusCount(openId,"+1")>0){
            Permission permission= permissionMapper.getPermission(openId);
            res= ResponseUtil.createResponse(Constant.SUCCESS,"增加成功");
            res.put("data",permission.getSendStatusCount());
        }
        else {
            res=ResponseUtil.createResponse(Constant.FAIL,"增加失败");
        }
        return new JSONObject(res);
    }

    /**
     * 增加用户可收到取消通知的数量
     * @param skey 用户的登录凭证
     * @return 规定格式返回值
     */
    @Override
    public JSONObject increaseCancelCount(String skey) {
        HashMap<String,Object> res;
        User user=userMapper.selectBySkey(skey);
        String openId=user.getOpenId();
        if(permissionMapper.updateCancelCount(openId,"+1")>0){
            Permission permission= permissionMapper.getPermission(openId);
            res= ResponseUtil.createResponse(Constant.SUCCESS,"增加成功");
            res.put("data",permission.getSendCancelCount());
        }
        else {
            res=ResponseUtil.createResponse(Constant.FAIL,"增加失败");
        }
        return new JSONObject(res);
    }
}
