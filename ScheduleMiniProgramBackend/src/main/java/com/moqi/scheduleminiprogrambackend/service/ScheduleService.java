package com.moqi.scheduleminiprogrambackend.service;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.vo.ScheduleVO;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public interface ScheduleService {

    JSONObject create(ScheduleVO scheduleVO);

    JSONObject delete(int scheduleId);

    JSONObject getScheduleByDate(Date startDate, Date endDate);

    /**
     * 更新日程的时间
     * @param timeInfo 包含日程Id，和新的时间
     * @return 请求结果
     */
    JSONObject modifyTime(Map<String,String> timeInfo);
}
