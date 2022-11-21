package com.github.makewheels.pushcenter;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("push")
public class PushController {
    @Resource
    private PushService pushService;

    @PostMapping("sendEmail")
    public JSONObject sendEmail(@RequestBody EmailRequest emailRequest) {
        return pushService.sendAliyunMail(emailRequest);
    }

}
