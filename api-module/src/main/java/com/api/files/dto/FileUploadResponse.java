package com.api.files.dto;

import java.time.LocalDateTime;

public class FileUploadResponse {

    private final String fileId;
    private final String fileUrl;
    private final LocalDateTime uploadedTime;

    public FileUploadResponse(String fileId, String fileUrl, LocalDateTime uploadedTime) {
        this.fileId = fileId;
        this.fileUrl = fileUrl;
        this.uploadedTime = uploadedTime;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public LocalDateTime getUploadedTime() {
        return uploadedTime;
    }
}
