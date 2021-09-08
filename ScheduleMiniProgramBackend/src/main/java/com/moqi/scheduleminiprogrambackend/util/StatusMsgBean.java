package com.moqi.scheduleminiprogrambackend.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class StatusMsgBean {

    private Name1 name1;

    private Time23 time23;

    private Time22 time22;

    private Thing2 thing2;

    private Phrase9 phrase9;


    @AllArgsConstructor
    @Data
    public static class Name1{
        private String value;
    }

    @AllArgsConstructor
    @Data
    public static class Time23{
        private String value;
    }

    @AllArgsConstructor
    @Data
    public static class Time22{
        private String value;
    }

    @AllArgsConstructor
    @Data
    public static class Thing2{
        private String value;
    }

    @AllArgsConstructor
    @Data
    public static class Phrase9{
        private String value;
    }
}
