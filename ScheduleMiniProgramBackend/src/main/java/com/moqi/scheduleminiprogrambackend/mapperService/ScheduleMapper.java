package com.moqi.scheduleminiprogrambackend.mapperService;

import com.moqi.scheduleminiprogrambackend.po.Schedule;

import java.sql.Date;
import java.util.List;

public interface ScheduleMapper {

    Integer insert(Schedule schedule);

    Integer delete(Integer id);

    List<Schedule> selectByDate(Date startDate,Date endDate);

    Schedule selectById(Integer id);

    Integer getLastestId();

    Integer updateTime(Schedule schedule);

}
