package com.moqi.scheduleminiprogrambackend.util;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

@Component
public class WeChatUtil {

    //秒转毫秒的进制
    private static final long SECONDS_TO_MILLISECONDS =1000L;
    //对于有效值减去60s，给予1分钟缓冲，防止程序运行过程中凭证失效
    private static final int SECONDS_OF_ONE_MINUTE =60;

    private static String appId;

    private static String secret;

    private static String accessToken;

    private static Date accessTokenDate;

    private static Integer accessTokenVilidTime;

    //参数注入
    @Value("${wx.app-id}")
    public void setAppId(String appId) {
        WeChatUtil.appId = appId;
    }

    @Value("${wx.secret}")
    public void setSecret(String secret) {
        WeChatUtil.secret = secret;
    }

    /**
     * 用于登录验证
     * @param code 前端传入的code
     * @return 返回sessionkey和openId
     */
    public static JSONObject getSessionByCode(String code) {
        String requestUrl = "https://api.weixin.qq.com/sns/jscode2session";
        HashMap<String, Object> requestUrlParam = new HashMap<>();
        //小程序appId
        requestUrlParam.put("appid", appId);
        //小程序secret
        requestUrlParam.put("secret", secret);
        //小程序端返回的code
        requestUrlParam.put("js_code", code);
        //默认参数
        requestUrlParam.put("grant_type", "authorization_code");
        //发送post请求读取调用微信接口获取openid用户唯一标识
        String result = HttpUtil.get(requestUrl, requestUrlParam);
        return JSON.parseObject(result);
    }

    /**
     * 重置凭证
     */
    public static void resetAccessToken(){
        //判断是否在有效期，还在有效期，可以不重置
        if(accessTokenDate!=null){
            Date dateNow=new Date();
            if((dateNow.getTime()-accessTokenDate.getTime())
                    <(accessTokenVilidTime-SECONDS_OF_ONE_MINUTE)*SECONDS_TO_MILLISECONDS){
                return;
            }
        }

        String requestUrl="https://api.weixin.qq.com/cgi-bin/token";
        HashMap<String, Object> requestUrlParam = new HashMap<>();
        //默认参数
        requestUrlParam.put("grant_type","client_credential");
        //小程序appId
        requestUrlParam.put("appid", appId);
        //小程序secret
        requestUrlParam.put("secret", secret);
        JSONObject result=JSON.parseObject(HttpUtil.get(requestUrl,requestUrlParam));
        //获取有效时间
        accessTokenVilidTime=result.getObject("expires_in",Integer.class);
        //获取当前时间
        accessTokenDate=new Date();
        //获取凭证
        accessToken=result.getObject("access_token",String.class);
    }

    /**
     * 发送订阅消息
     * @return 请求的返回体
     */
    public static String sendAppointmentMsg(String openId,String name,String place,String startTime,String endTime,String content){
        resetAccessToken();
        String requestUrl="https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken;
        HashMap<String, Object> requestUrlParam = new HashMap<>();
        String toUser=openId;
        //发送预约请求的模板Id
        String templateId="D9rT-8zRcF-YRyS2vNQQHc4IzXXvrMH9qxhC8eBiwRw";
        String page="pages/myAppointment/myAppointment";

        //构建请求体数据
        AppointmentMsgBean data=new AppointmentMsgBean();
        data.setName5(new AppointmentMsgBean.Name5(name));
        data.setThing2(new AppointmentMsgBean.Thing2(place));
        data.setDate3(new AppointmentMsgBean.Date3(startTime));
        data.setDate4(new AppointmentMsgBean.Date4(endTime));
        data.setThing6(new AppointmentMsgBean.Thing6(content));

        //构建最外层请求参数
        requestUrlParam.put("touser",toUser);
        requestUrlParam.put("template_id",templateId);
        requestUrlParam.put("data",data);
        requestUrlParam.put("page",page);

        String para=JSON.toJSONString(requestUrlParam);
        //发出请求
        JSONObject result=JSON.parseObject(HttpUtil.post(requestUrl,para));

        System.out.println(result);
        return result.getString("errmsg");
    }

    public static String sendStatusMsg(String openId,String name,String place,String startTime,String endTime,String result){
        resetAccessToken();
        String requestUrl="https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken;
        HashMap<String, Object> requestUrlParam = new HashMap<>();
        String toUser=openId;
        //发送预约状态变更的模板Id
        String templateId="s508HTgz9M7ZfePv6VCj-Ji54iUbXQmGUUZw_EXrkGk";
        String page="pages/myAppointment/myAppointment";

        //构建data数据
        StatusMsgBean data=new StatusMsgBean();
        data.setName1(new StatusMsgBean.Name1(name));
        data.setTime22(new StatusMsgBean.Time22(startTime));
        data.setTime23(new StatusMsgBean.Time23(endTime));
        data.setThing2(new StatusMsgBean.Thing2(place));
        data.setPhrase9(new StatusMsgBean.Phrase9(result));

        //构建最外层请求参数
        requestUrlParam.put("touser",toUser);
        requestUrlParam.put("template_id",templateId);
        requestUrlParam.put("data",data);
        requestUrlParam.put("page",page);

        String para=JSON.toJSONString(requestUrlParam);
        //发出请求
        JSONObject res=JSON.parseObject(HttpUtil.post(requestUrl,para));

        System.out.println(res);
        return res.getString("errmsg");
    }

    public static String sendCancelMsg(String openId,String name,String place,String date,String startTime,String endTime,String reason){
        resetAccessToken();
        String requestUrl="https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token="+accessToken;
        HashMap<String, Object> requestUrlParam = new HashMap<>();
        String toUser=openId;
        //发送预约状态变更的模板Id
        String templateId="-mGz-2oSlZIrm-nfK6V2jj7sUSq3v1cEn6x8x-aSvyo";
        String page="pages/index/index";

        //构建data数据
        CancelMsgBean data=new CancelMsgBean();
        data.setThing5(new CancelMsgBean.Thing5(name));
        data.setCharacter_string6(new CancelMsgBean.CharacterString6(date+" "+startTime+"-"+endTime));
        data.setThing8(new CancelMsgBean.Thing8(place));
        data.setThing7(new CancelMsgBean.Thing7(reason==null?"时间冲突":reason));

        //构建最外层请求参数
        requestUrlParam.put("touser",toUser);
        requestUrlParam.put("template_id",templateId);
        requestUrlParam.put("data",data);
        requestUrlParam.put("page",page);

        String para=JSON.toJSONString(requestUrlParam);
        //发出请求
        JSONObject res=JSON.parseObject(HttpUtil.post(requestUrl,para));

        System.out.println(res);
        return res.getString("errmsg");
    }

    /*public static void main(String[] args) {
        WeChatUtil.sendStatusMsg("ocRf55HXnnP51wXOsPsSGgxu","张刘洋",
                "食堂","2021-8-30","10:42","时间冲突");
    }*/

   /* public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) throws Base64DecodingException {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);

        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            // 初始化
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSON.parseObject(result);
            }
        } catch (Exception e) {
        }
        return null;
    }*/
}
