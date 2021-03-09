package com.douyuehan.doubao.utils;


import com.baidubce.auth.DefaultBceCredentials;
import com.baidubce.services.sms.SmsClient;
import com.baidubce.services.sms.SmsClientConfiguration;
import com.baidubce.services.sms.model.SendMessageV3Request;
import com.baidubce.services.sms.model.SendMessageV3Response;

import java.util.HashMap;
import java.util.Map;

public class BaiduSmsComponent {

    public static final String ACCESS_KEY_ID = "59f8d1be79b34f20bd0ef0773274cfc4";
    public static final String SECRET_ACCESS_KEY = "";
    public static final String ENDPOINT = "http://smsv3.bj.baidubce.com";
    public static final String SIGNATUREID="sms-sign-lPkNTp18587";
    public static final String TEMPLATE="sms-tmpl-eYtbPR18643";

    public static String send(String phone,String code){
        SmsClientConfiguration config = new SmsClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        config.setEndpoint(ENDPOINT);
        SmsClient client = new SmsClient(config);

        SendMessageV3Request request = new SendMessageV3Request();
        request.setMobile(phone);
        request.setSignatureId(SIGNATUREID);
        request.setTemplate(TEMPLATE);
        Map<String, String> contentVar = new HashMap<>();
        contentVar.put("code", code);
        contentVar.put("time", "15");
        request.setContentVar(contentVar);
        SendMessageV3Response response = client.sendMessage(request);
        // 解析请求响应 response.isSuccess()为true 表示成功
        if (response != null && response.isSuccess()) {
            return "success";
        } else {
            return "fail";
        }
    }


    public static void main(String... args) {
        SmsClientConfiguration config = new SmsClientConfiguration();
        config.setCredentials(new DefaultBceCredentials(ACCESS_KEY_ID, SECRET_ACCESS_KEY));
        config.setEndpoint(ENDPOINT);
        SmsClient client = new SmsClient(config);

        SendMessageV3Request request = new SendMessageV3Request();
        request.setMobile("15950007710");
        request.setSignatureId(SIGNATUREID);
        request.setTemplate(TEMPLATE);
        Map<String, String> contentVar = new HashMap<>();
        contentVar.put("code", "23456");
        contentVar.put("time", "1");
        request.setContentVar(contentVar);
        SendMessageV3Response response = client.sendMessage(request);
        // 解析请求响应 response.isSuccess()为true 表示成功
        if (response != null && response.isSuccess()) {
            System.out.println("success");
        } else {
            System.out.println("fail");
        }
    }

}
