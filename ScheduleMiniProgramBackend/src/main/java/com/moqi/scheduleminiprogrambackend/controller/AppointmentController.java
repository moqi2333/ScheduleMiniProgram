package com.moqi.scheduleminiprogrambackend.controller;

import com.alibaba.fastjson.JSONObject;
import com.moqi.scheduleminiprogrambackend.service.AppointmentService;
import com.moqi.scheduleminiprogrambackend.vo.AppointmentVO;
import com.moqi.scheduleminiprogrambackend.vo.FeedbackListItemVO;
import com.moqi.scheduleminiprogrambackend.vo.FeedbackVO;
import com.moqi.scheduleminiprogrambackend.vo.RecordVO;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Date;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    @Resource
    AppointmentService appointmentService;

    @PostMapping("/create")
    public JSONObject create(@RequestParam Map<String,String> appointmentInfo){
        return appointmentService.create(appointmentInfo);
    }

    @PostMapping("/cancel")
    public JSONObject cancel(@RequestParam(name = "skey") String skey,
                             @RequestParam(name = "appointmentId") int id){
        return appointmentService.cancel(skey,id);
    }

    @GetMapping("/getAppointmentByDate")
    public JSONObject getAppointmentByDate(@RequestParam(name = "startDate")Date startDate,
                                           @RequestParam(name = "endDate") Date endDate,
                                           @RequestParam(name = "skey",required = false) String skey){
        return appointmentService.getAppointmentByDate(startDate,endDate,skey);
    }

    @PostMapping("/record")
    public JSONObject record(@RequestParam(name = "appointmentId") int id,
                             @RequestParam(name = "record") String record){
        return appointmentService.updateRecord(id,record);
    }

    @GetMapping("/getRecordList")
    public JSONObject getRecordList(@RequestParam(name = "pageIndex") int pageIndex,
                                    @RequestParam(name = "pageSize") int pageSize,
                                    @RequestParam(name = "status") int status){
        return appointmentService.getRecordList(pageIndex,pageSize,status);
    }

    @GetMapping("/getRecord")
    public RecordVO getRecord(@RequestParam(name = "appointmentId") int appointmentId){
        return appointmentService.getRecord(appointmentId);
    }

    @PostMapping("/feedback")
    public JSONObject feedback(@RequestParam Map<String,String> feedbackInfo){
        return appointmentService.updateFeedback(feedbackInfo);
    }

    @GetMapping("/getFeedbackList")
    public JSONObject getFeedbackList(@RequestParam(name = "pageIndex") int pageIndex,
                                      @RequestParam(name = "pageSize") int pageSize,
                                      @RequestParam(name = "status") int status){
        return appointmentService.getFeedbackList(pageIndex,pageSize,status);
    }

    @GetMapping("/getFeedback")
    public FeedbackVO getFeedback(@RequestParam(name = "appointmentId") int appointmentId){
        return appointmentService.getFeedback(appointmentId);
    }

    @PostMapping("/modifyTime")
    public JSONObject modifyTime(@RequestParam Map<String,String> timeInfo){
        return appointmentService.updateTime(timeInfo);
    }

    @GetMapping("/getAppointmentByStatus")
    public JSONObject getAppointmentByStatus(@RequestParam(name = "status")int status,
                                                      @RequestParam(name = "pageIndex") int pageIndex,
                                                      @RequestParam(name = "pageSize") int pageSize){
        return appointmentService.getAppointmentByStatus(status,pageIndex,pageSize);
    }

    @PostMapping("/handleAppointment")
    public JSONObject handleAppointment(@RequestParam(name = "appointmentId") int appointmentId,
                                        @RequestParam(name = "status") int status){
        return appointmentService.updateStatus(appointmentId,status);
    }

    @GetMapping("/getStudentAppointmentByStatus")
    public List<AppointmentVO> getStudentAppointmentByStatus(@RequestParam(name = "skey") String skey,
                                                             @RequestParam(name = "status") int status){
        return appointmentService.getStudentAppointmentByStatus(skey,status);
    }

    @GetMapping("/getStudentFeedbackByStatus")
    public List<FeedbackListItemVO> getStudentFeedbackByStatus(@RequestParam(name = "skey") String skey,
                                                               @RequestParam(name = "status") int status){
        return appointmentService.getStudentFeedbackByStatus(skey, status);
    }

    @PostMapping("/getForm")
    public JSONObject getForm(@RequestParam(name = "userIds") List<Integer> userIds){
        return appointmentService.getForm(userIds);
    }


}
