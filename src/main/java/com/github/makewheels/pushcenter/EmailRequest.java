package com.github.makewheels.pushcenter;

import lombok.Data;

@Data
public class EmailRequest {
    private String toAddress;
    private String fromAlias;
    private String subject;
    private String htmlBody;
}
