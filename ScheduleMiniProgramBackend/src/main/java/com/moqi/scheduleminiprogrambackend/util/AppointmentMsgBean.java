package com.moqi.scheduleminiprogrambackend.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AppointmentMsgBean {

    private Name5 name5;
    private Thing2 thing2;
    private Date3 date3;
    private Date4 date4;
    private Thing6 thing6;

    @Data
    @AllArgsConstructor
    public static class Name5{
        private String value;
    }

    @Data
    @AllArgsConstructor
    public static class Thing2{
        private String value;
    }

    @Data
    @AllArgsConstructor
    public static class Date3{
        private String value;
    }

    @Data
    @AllArgsConstructor
    public static class Date4{
        private String value;
    }

    @Data
    @AllArgsConstructor
    public static class Thing6{
        private String value;
    }
}
