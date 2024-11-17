package com.api.files;

import java.time.LocalDateTime;

public class FileUploadResponse {

    private String fileId;
    private String fileUrl;
    private LocalDateTime uploadedTime;

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
