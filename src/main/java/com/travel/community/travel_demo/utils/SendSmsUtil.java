package com.travel.community.travel_demo.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.beans.factory.annotation.Value;

import java.util.Random;


/**
 * @Author w1586
 * @Date 2020/3/14 21:46
 * @Cersion 1.0
 */
public class SendSmsUtil {

    @Value("${aliyun.sms.accessKeyId}")
    public String accessKeyId;

    @Value("${p5VaKznLqCHCkiQQO5R8Kxk0pNnL8K}")
    public String accessSecret;

    /**
     * 随机验证码
     */
    private int phoneCode;

    public int getPhoneCode() {
        return phoneCode;
    }

    public void setPhoneCode() {
        /**
         * 会生成一个0~9999的随机数，
         * 用code=(int)(Math.random()*9999)+10000;得到的code会有除0的情况
         */
        phoneCode = new Random().nextInt(10000);
    }


    public boolean sendPhoneText(String phoneNum){

        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessSecret);
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneNum);
        request.putQueryParameter("SignName", "码匠社区");
        request.putQueryParameter("TemplateCode", "SMS_182669487");
        request.putQueryParameter("TemplateParam", "{\"code\":"+phoneCode+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return true;
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }

        return false;
    }


}
