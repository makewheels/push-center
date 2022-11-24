package com.github.makewheels.pushcenter;

import cn.hutool.core.codec.Base64;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AliyunMailService {
    public JSONObject send(String toAddress, String fromAlias, String subject, String htmlBody) {
        IClientProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",
                Base64.decodeStr("TFRBSTV0Q0w3NzdxR2duZFk5Z1Fodmlm"),
                Base64.decodeStr("VlQyYmlSb1hKT041YTJKRjVTdnhRRjUyS2xpYjdB"));
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();

        request.setAccountName("master@mail.aliyun.pushcenter.cc");
        request.setFromAlias(fromAlias);//发信人昵称，长度小于15个字符。
        request.setAddressType(1);
        request.setReplyToAddress(false);// 是否启用管理控制台中配置好回信地址
        request.setToAddress(toAddress);
        //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
        //request.setToAddress("邮箱1,邮箱2");
        request.setSubject(subject);
        //注意：文本邮件的大小限制为3M，过大的文本会导致连接超时或413错误
        request.setHtmlBody(htmlBody);
        request.setMethod(MethodType.POST);
        try {
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            log.info("阿里云发邮件, toAddress = " + toAddress + ", subject =" + subject);
            String json = JSON.toJSONString(httpResponse);
            log.info(json);
            return JSONObject.parseObject(json);
        } catch (ClientException e) {
            log.error("阿里云发邮件错误：");
            log.error("ErrCode = " + e.getErrCode());
            e.printStackTrace();
            return null;
        }
    }
}
