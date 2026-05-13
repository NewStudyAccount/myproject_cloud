package com.project.cloud.api.generator.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class RemoteGenResult implements Serializable {

    private Map<String, String> codeMap;
}