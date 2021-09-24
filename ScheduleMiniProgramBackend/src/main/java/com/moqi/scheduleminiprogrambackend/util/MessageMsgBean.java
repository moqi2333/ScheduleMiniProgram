package com.moqi.scheduleminiprogrambackend.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class MessageMsgBean {


    private Name1 name1;

    private Time2 time2;

    private Thing5 thing5;

    private Thing3 thing3;

    @AllArgsConstructor
    @Data
    public static class Name1{
        private String value;
    }

    @AllArgsConstructor
    @Data
    public static class Time2{
        private String value;
    }


    @AllArgsConstructor
    @Data
    public static class Thing5{
        private String value;
    }


    @AllArgsConstructor
    @Data
    public static class Thing3{
        private String value;
    }
}
