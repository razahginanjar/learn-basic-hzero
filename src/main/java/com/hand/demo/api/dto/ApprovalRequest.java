package com.hand.demo.api.dto;


import java.util.List;

public class ApprovalRequest {
    private List<Long> taskIds;
    private String comments;

    public List<Long> getTaskIds() {
        return taskIds;
    }

    public void setTaskIds(List<Long> taskIds) {
        this.taskIds = taskIds;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ApprovalRequest(List<Long> taskIds, String comments) {
        this.taskIds = taskIds;
        this.comments = comments;
    }

    public ApprovalRequest() {
    }
}
