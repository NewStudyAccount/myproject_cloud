package com.project.cloud.common.log.event;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperLogEvent {

    private String title;
    private Integer businessType;
    private String method;
    private String requestParam;
    private String jsonResult;
    private Integer status;
    private String errorMsg;
    private Long costTime;
    private LocalDateTime operTime;
    private String operIp;
    private String operName;
}
