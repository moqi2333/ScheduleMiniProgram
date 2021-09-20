package com.moqi.scheduleminiprogrambackend.serviceImpl;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.mapperService.ScheduleMapper;
import com.moqi.scheduleminiprogrambackend.po.Schedule;
import com.moqi.scheduleminiprogrambackend.service.ScheduleService;
import com.moqi.scheduleminiprogrambackend.util.Constant;
import com.moqi.scheduleminiprogrambackend.util.DateUtil;
import com.moqi.scheduleminiprogrambackend.util.ResponseUtil;
import com.moqi.scheduleminiprogrambackend.vo.ScheduleVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

/**
 * @author moqi
 */
@Service
public class ScheduleServiceImpl implements ScheduleService {

    @Resource
    ScheduleMapper scheduleMapper;

    @Override
    public JSONObject create(ScheduleVO scheduleVO) {
        HashMap<String,Object> res;
        if(scheduleVO.getStartTime()==null||scheduleVO.getEndTime()==null
                ||scheduleVO.getStartTime().length()!=5||scheduleVO.getEndTime().length()!=5){
            res=ResponseUtil.createResponse(Constant.FAIL,"未填写时间,请正确填写时间段");
            return new JSONObject(res);
        }
        //判断是否填写地点和内容
        if("".equals(scheduleVO.getPlace())|| "".equals(scheduleVO.getContent())){
            res=ResponseUtil.createResponse(Constant.FAIL,"未填写地点或内容，请重新填写");
            return new JSONObject(res);
        }
        Schedule schedule=new Schedule(scheduleVO);
        if(schedule.getStartTime().after(schedule.getEndTime())){
            res=ResponseUtil.createResponse(Constant.FAIL,"开始时间不能大于结束时间");
            return new JSONObject(res);
        }
        if(scheduleMapper.insert(schedule)>0){
            int scheduleId=scheduleMapper.getLastestId();
            res=ResponseUtil.createResponse(Constant.SUCCESS,"创建成功");
            res.put("scheduleId",scheduleId);

        }else {
            res=ResponseUtil.createResponse(Constant.FAIL,"创建失败，数据库插入发生错误");
        }
        return new JSONObject(res);
    }

    @Override
    public JSONObject delete(int scheduleId) {
        HashMap<String,Object> res;
        if(scheduleMapper.delete(scheduleId)>0){
            res=ResponseUtil.createResponse(Constant.SUCCESS,"删除成功");
        }else {
            res=ResponseUtil.createResponse(Constant.FAIL,"删除失败,请检查scheduleId是否错误");
        }
        return new JSONObject(res);
    }

    @Override
    public JSONObject getScheduleByDate(Date startDate, Date endDate) {
        List<Schedule> scheduleList=scheduleMapper.selectByDate(startDate, endDate);
        if(scheduleList==null){
            throw new RuntimeException("数据库查询错误");
        }
        HashMap<String,Object> res=new HashMap<>();
        //先排序
        scheduleList.sort(Comparator.comparing(Schedule::getDate));
        //构造返回值需要的结构
        Date dateTmp=new Date(startDate.getTime());
        int listIndex=0;
        int jsonIndex=1;
        int length=scheduleList.size();
        ArrayList<ScheduleVO> scheduleVOS=new ArrayList<>();

        while (!dateTmp.after(endDate)){
            //双指针实现，当前日期指针和当前列表指针的日期相同，就将元素插入列表中，并移动列表指针
            if(listIndex<length&& scheduleList.get(listIndex).getDate().equals(dateTmp)){
                scheduleVOS.add(new ScheduleVO(scheduleList.get(listIndex)));
                listIndex++;
            }
            //不相同就将列表加入返回体，移动日期指针,并清空列表
            else {
                dateTmp= DateUtil.getNextDay(dateTmp);
                res.put(String.valueOf(jsonIndex),scheduleVOS);
                jsonIndex++;
                scheduleVOS=new ArrayList<>();
            }
        }
        return new JSONObject(res);
    }

    /**
     * 更新日程的时间
     *
     * @param timeInfo 包含日程Id，和新的时间
     * @return 请求结果
     */
    @Override
    public JSONObject modifyTime(Map<String, String> timeInfo) {
        HashMap<String,Object> res;
        int scheduleId=Integer.parseInt(timeInfo.get("scheduleId"));
        Time startTime=Time.valueOf(timeInfo.get("startTime")+":00");
        Time endTime=Time.valueOf(timeInfo.get("endTime")+":00");
        if(startTime.after(endTime)){
            res=ResponseUtil.createResponse(Constant.FAIL,"开始时间不能大于结束时间");
            return new JSONObject(res);
        }
        Schedule schedule=scheduleMapper.selectById(scheduleId);
        if(schedule==null){
            res= ResponseUtil.createResponse(Constant.FAIL, "修改失败，未找到该日程");
            return new JSONObject(res);
        }
        schedule.setStartTime(startTime);
        schedule.setEndTime(endTime);
        if(scheduleMapper.updateTime(schedule)>0){
            res= ResponseUtil.createResponse(Constant.SUCCESS, "修改成功");
            return new JSONObject(res);
        }else {
            res= ResponseUtil.createResponse(Constant.FAIL, "修改失败，数据库发生未知错误");
            return new JSONObject(res);
        }
    }
}
