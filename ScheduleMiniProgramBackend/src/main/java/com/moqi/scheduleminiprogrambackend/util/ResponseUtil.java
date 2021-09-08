package com.moqi.scheduleminiprogrambackend.util;

import java.util.HashMap;

public class ResponseUtil {

    /**
     * 创建一个标准的返回体
     * @param code 表明是否成功
     * @param msg 返回的信息
     * @return 用于构建JsonObject的hashmap
     */
    public static HashMap<String,Object> createResponse(Integer code, String msg){
        HashMap<String,Object> res=new HashMap<>();
        res.put("code",code);
        res.put("msg",msg);
        return res;
    }
}
