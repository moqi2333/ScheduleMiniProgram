package com.moqi.scheduleminiprogrambackend.mapperService;

import com.moqi.scheduleminiprogrambackend.po.Permission;

public interface PermissionMapper {

    Integer insert(String openId);

    Integer updateAppointmentCount(String openId,String option);

    Integer updateStatusCount(String openId,String option);

    Integer updateCancelCount(String openId,String option);

    Permission getPermission(String openId);
}
