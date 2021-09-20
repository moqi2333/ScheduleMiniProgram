package com.moqi.scheduleminiprogrambackend.mapperService;

import cn.hutool.core.date.DateTime;
import com.moqi.scheduleminiprogrambackend.po.Appointment;

import java.sql.Date;
import java.util.List;

public interface AppointmentMapper {

    Integer insert(Appointment appointment);

    Integer delete(Integer appointmentId);

    List<Appointment> getAppointmentByDate(Date startDate,Date endDate);

    Integer getLatestId();

    Appointment getAppointmentById(Integer id);

    Integer updateTime(Appointment appointment);

    Integer updateRecord(Integer appointmentId, String record, DateTime recordTime);

    Integer updateFeedback(Integer appointmentId,String feedback,DateTime feedbackTime);

    Integer updateStatus(Integer appointmentId ,Integer status);

    List<Appointment> getAppointmentByStatus(Integer status);

    List<Appointment> getAllValid();

    List<Appointment> getAllByRecordStatus(String isNull);

    List<Appointment> getAllByFeedbackStatus(String isNull);

    List<Appointment> getStudentAppointmentByStatus(String openId,int status);

    List<Appointment> getStudentFeedbackByStatus(String openId,String isNull);

    List<Appointment> getAppointmentByOpenId(String openId);

}
