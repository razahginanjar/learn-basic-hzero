package com.hand.demo.api.dto.response;

import com.hand.demo.infra.constant.ConstantFileStatus;
import org.mapstruct.Builder;

public class FileResponse {
    private String fileName;
    private Boolean statusUpload;
    private ConstantFileStatus status;

    public FileResponse() {
    }

    public FileResponse(String fileName, Boolean statusUpload, ConstantFileStatus status) {
        this.fileName = fileName;
        this.statusUpload = statusUpload;
        this.status = status;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Boolean getStatusUpload() {
        return statusUpload;
    }

    public void setStatusUpload(Boolean statusUpload) {
        this.statusUpload = statusUpload;
    }

    public ConstantFileStatus getStatus() {
        return status;
    }

    public void setStatus(ConstantFileStatus status) {
        this.status = status;
    }
}
