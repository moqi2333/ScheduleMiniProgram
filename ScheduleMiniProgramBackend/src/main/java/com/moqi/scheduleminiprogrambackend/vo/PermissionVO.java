package com.moqi.scheduleminiprogrambackend.vo;

import com.moqi.scheduleminiprogrambackend.po.Permission;

public class PermissionVO {

    private int sendAppointmentCount;

    private int sendStatusCount;

    private int sendCancelCount;

    public PermissionVO(Permission permission){
        this.sendAppointmentCount = permission.getSendAppointmentCount();
        this.sendStatusCount = permission.getSendStatusCount();
        this.sendCancelCount = permission.getSendCancelCount();
    }

    public PermissionVO(String openId, int sendAppointmentCount, int sendStatusCount, int sendCancelCount) {
        this.sendAppointmentCount = sendAppointmentCount;
        this.sendStatusCount = sendStatusCount;
        this.sendCancelCount = sendCancelCount;
    }

    public int getSendAppointmentCount() {
        return sendAppointmentCount;
    }

    public void setSendAppointmentCount(int sendAppointmentCount) {
        this.sendAppointmentCount = sendAppointmentCount;
    }

    public int getSendStatusCount() {
        return sendStatusCount;
    }

    public void setSendStatusCount(int sendStatusCount) {
        this.sendStatusCount = sendStatusCount;
    }

    public int getSendCancelCount() {
        return sendCancelCount;
    }

    public void setSendCancelCount(int sendCancelCount) {
        this.sendCancelCount = sendCancelCount;
    }
}
