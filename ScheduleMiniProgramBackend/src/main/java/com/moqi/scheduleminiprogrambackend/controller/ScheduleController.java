package com.moqi.scheduleminiprogrambackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.service.ScheduleService;
import com.moqi.scheduleminiprogrambackend.vo.ScheduleVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/schedule")
public class ScheduleController {


    @Resource
    ScheduleService scheduleService;


    @PostMapping("/create")
    public JSONObject create(ScheduleVO scheduleVO){
        return scheduleService.create(scheduleVO);
    }


    @PostMapping("/delete")
    public JSONObject delete(@RequestParam int scheduleId){
        return scheduleService.delete(scheduleId);
    }


    @GetMapping("/getScheduleByDate")
    public JSONObject getScheduleByDate(@RequestParam(name = "startDate")Date startDate,
                                             @RequestParam(name = "endDate") Date endDate){
        return scheduleService.getScheduleByDate(startDate, endDate);
    }


    @PostMapping("/modifyTime")
    public JSONObject modifyTime(@RequestParam HashMap<String,String> timeInfo){
        return scheduleService.modifyTime(timeInfo);
    }
}
