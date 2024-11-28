package com.hand.demo.api.dto;

import com.hand.demo.infra.constant.StatusDoc;

import java.time.LocalDateTime;
import java.util.Date;

public class InvCountRequest {
    private String businessKey;
    private String statusDoc;
    private Date approvedTime;
    private Long workFlowId;

    public InvCountRequest() {
    }

    public InvCountRequest(String businessKey, String statusDoc, Date approvedTime, Long workFlowId) {
        this.businessKey = businessKey;
        this.statusDoc = statusDoc;
        this.approvedTime = approvedTime;
        this.workFlowId = workFlowId;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getStatusDoc() {
        return statusDoc;
    }

    public void setStatusDoc(String statusDoc) {
        this.statusDoc = statusDoc;
    }

    public Date getApprovedTime() {
        return approvedTime;
    }

    public void setApprovedTime(Date approvedTime) {
        this.approvedTime = approvedTime;
    }

    public Long getWorkFlowId() {
        return workFlowId;
    }

    public void setWorkFlowId(Long workFlowId) {
        this.workFlowId = workFlowId;
    }
}
