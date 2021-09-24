package com.moqi.scheduleminiprogrambackend.po;

public class Permission {

    private String openId;

    private int sendAppointmentCount;

    private int sendStatusCount;

    private int sendCancelCount;

    private int sendMessageCount;


    public Permission(String openId, int sendAppointmentCount, int sendStatusCount, int sendCancelCount, int sendMessageCount) {
        this.openId = openId;
        this.sendAppointmentCount = sendAppointmentCount;
        this.sendStatusCount = sendStatusCount;
        this.sendCancelCount = sendCancelCount;
        this.sendMessageCount = sendMessageCount;
    }

    public Permission() {
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
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
