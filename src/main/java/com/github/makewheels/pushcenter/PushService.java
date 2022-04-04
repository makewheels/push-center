package com.github.makewheels.pushcenter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PushService {
    @Resource
    private AliyunMailService aliyunMailService;

    public JSONObject sendAliyunMail(EmailRequest emailRequest) {
        return aliyunMailService.send(emailRequest.getToAddress(), emailRequest.getFromAlias(),
                emailRequest.getSubject(), emailRequest.getHtmlBody());
    }

    public JSONObject sendPushplus(PushplusRequest pushplusRequest) {

        return null;
    }
}
