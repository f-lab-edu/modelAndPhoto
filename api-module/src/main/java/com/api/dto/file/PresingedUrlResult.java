package com.api.dto.file;

public class PresingedUrlResult {

    public String url;
    public String fileId;

    public PresingedUrlResult(String url, String fileId) {
        this.url = url;
        this.fileId = fileId;
    }

    public String getUrl() {
        return url;
    }

    public String getFileId() {
        return fileId;
    }
}
