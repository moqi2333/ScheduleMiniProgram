package com.moqi.scheduleminiprogrambackend.vo;

import com.moqi.scheduleminiprogrambackend.po.Permission;

public class PermissionVO {

    private int sendAppointmentCount;

    private int sendStatusCount;

    private int sendCancelCount;

    private int sendMessageCount;

    public PermissionVO(Permission permission){
        this.sendAppointmentCount = permission.getSendAppointmentCount();
        this.sendStatusCount = permission.getSendStatusCount();
        this.sendCancelCount = permission.getSendCancelCount();
        this.sendMessageCount=permission.getSendMessageCount();
    }

    public PermissionVO(int sendAppointmentCount, int sendStatusCount, int sendCancelCount, int sendMessageCount) {
        this.sendAppointmentCount = sendAppointmentCount;
        this.sendStatusCount = sendStatusCount;
        this.sendCancelCount = sendCancelCount;
        this.sendMessageCount = sendMessageCount;
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

    public int getSendMessageCount() {
        return sendMessageCount;
    }

    public void setSendMessageCount(int sendMessageCount) {
        this.sendMessageCount = sendMessageCount;
    }
}
