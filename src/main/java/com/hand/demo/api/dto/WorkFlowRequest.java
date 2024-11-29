package com.hand.demo.api.dto;

import java.util.Map;

public class WorkFlowRequest {
    private Long tenantId;
    private String flowKey;
    private String businessKey;
    private String dimension;
    private String starter;
    private Map<String, Object> variableMap;

    public WorkFlowRequest() {
    }

    public WorkFlowRequest(Long tenantId, String flowKey, String businessKey, String dimension, String starter, Map<String, Object> variableMap) {
        this.tenantId = tenantId;
        this.flowKey = flowKey;
        this.businessKey = businessKey;
        this.dimension = dimension;
        this.starter = starter;
        this.variableMap = variableMap;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public void setTenantId(Long tenantId) {
        this.tenantId = tenantId;
    }

    public String getFlowKey() {
        return flowKey;
    }

    public void setFlowKey(String flowKey) {
        this.flowKey = flowKey;
    }

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
    }

    public String getDimension() {
        return dimension;
    }

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public String getStarter() {
        return starter;
    }

    public void setStarter(String starter) {
        this.starter = starter;
    }

    public Map<String, Object> getVariableMap() {
        return variableMap;
    }

    public void setVariableMap(Map<String, Object> variableMap) {
        this.variableMap = variableMap;
    }
}
