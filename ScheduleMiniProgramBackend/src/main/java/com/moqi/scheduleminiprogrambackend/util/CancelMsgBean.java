package com.moqi.scheduleminiprogrambackend.util;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CancelMsgBean {

    private Thing5 thing5;

    private CharacterString6 character_string6;

    private Thing8 thing8;

    private Thing7 thing7;


    @AllArgsConstructor
    @Data
    public static class Thing5{
        private String value;
    }

    @AllArgsConstructor
    @Data
    public static class CharacterString6{
        private String value;
    }

    @AllArgsConstructor
    @Data
    public static class Thing8{
        private String value;
    }

    @AllArgsConstructor
    @Data
    public static class Thing7{
        private String value;
    }
}
