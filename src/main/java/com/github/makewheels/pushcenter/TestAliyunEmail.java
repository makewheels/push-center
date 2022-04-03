package com.github.makewheels.pushcenter;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.URLUtil;
import com.alibaba.fastjson.JSON;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dm.model.v20151123.SingleSendMailRequest;
import com.aliyuncs.dm.model.v20151123.SingleSendMailResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;

public class TestAliyunEmail {
    public static void main(String[] args) {
        IClientProfile profile = DefaultProfile.getProfile(
                "cn-hangzhou",
                Base64.decodeStr("TFRBSTV0Q0w3NzdxR2duZFk5Z1Fodmlm"),
                Base64.decodeStr("VlQyYmlSb1hKT041YTJKRjVTdnhRRjUyS2xpYjdB"));
        IAcsClient client = new DefaultAcsClient(profile);
        SingleSendMailRequest request = new SingleSendMailRequest();
        try {
            request.setAccountName("master@mail.aliyun.java8.icu");
            request.setFromAlias("寝室电费");//发信人昵称，长度小于15个字符。
            request.setAddressType(1);
            request.setReplyToAddress(false);// 是否启用管理控制台中配置好回信地址
            request.setToAddress("finalbird@foxmail.com");
            //可以给多个收件人发送邮件，收件人之间用逗号分开，批量发信建议使用BatchSendMailRequest方式
            //request.setToAddress("邮箱1,邮箱2");
            request.setSubject("发送id:" + IdUtil.nanoId());
            //如果采用byte[].toString的方式的话请确保最终转换成utf-8的格式再放入htmlbody和textbody，若编码不一致则会被当成垃圾邮件。
            //注意：文本邮件的大小限制为3M，过大的文本会导致连接超时或413错误
            request.setHtmlBody("您好：<br>" +
                    "￥38.51<br>"
                    + "<a href=\"http://mail.aliyun.java8.icu:5025/push/unscbstrcb\">退订</a>");
            request.setMethod(MethodType.POST);
            //开启需要备案，0关闭，1开启
            //request.setClickTrace("0");
            //如果调用成功，正常返回httpResponse；如果调用失败则抛出异常，
            // 需要在异常中捕获错误异常码；错误异常码请参考对应的API文档;
            SingleSendMailResponse httpResponse = client.getAcsResponse(request);
            System.out.println(JSON.toJSONString(httpResponse));
        } catch (ClientException e) {
            //捕获错误异常码
            System.out.println("ErrCode : " + e.getErrCode());
            e.printStackTrace();
        }
    }
}
