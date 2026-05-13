package com.project.cloud.api.file.domain;

import lombok.Data;

import java.io.Serializable;

@Data
public class RemoteFileInfo implements Serializable {

    private Long id;
    private String fileName;
    private String originalName;
    private String filePath;
    private String fileUrl;
    private Long fileSize;
    private String contentType;
}