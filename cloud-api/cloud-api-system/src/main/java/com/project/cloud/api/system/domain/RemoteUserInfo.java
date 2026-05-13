package com.project.cloud.api.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RemoteUserInfo implements Serializable {

    private Long id;
    private String username;
    private String password;
    private Integer status;
    private List<String> roles;
}