package com.hand.demo.app.service.impl;

import com.hand.demo.api.dto.WorkFlowRequestDTO;
import com.hand.demo.app.service.WorkFlowService;
import io.choerodon.core.domain.Page;
import org.hzero.boot.workflow.WorkflowClient;
import org.hzero.boot.workflow.dto.PersonalTodoDTO;
import org.hzero.boot.workflow.dto.ProcessInstanceDTO;
import org.hzero.boot.workflow.dto.RunInstance;
import org.hzero.boot.workflow.dto.RunTaskHistory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    private final WorkflowClient workflowClient;

    public WorkFlowServiceImpl(WorkflowClient workflowClient) {
        this.workflowClient = workflowClient;
    }

    @Override
    public RunInstance start(Long tenantId, WorkFlowRequestDTO request) {
        return workflowClient.startInstanceByFlowKey(
                tenantId, request.getFlowKey(), request.getBusinessKey(), request.getDimension()
                , request.getStarter(), request.getVariableMap()
        );
    }

    @Override
    public Page<PersonalTodoDTO.PersonalTodoViewDTO> myTodoList(
            Long tenantId, Long page, Long size, String flowName) {
        PersonalTodoDTO.PersonalTodoQueryDTO personalTodoQueryDTO
                = new PersonalTodoDTO.PersonalTodoQueryDTO();
        personalTodoQueryDTO.setFlowName(flowName);
        return workflowClient.todoPage(tenantId, Math.toIntExact(page),
                Math.toIntExact(size), personalTodoQueryDTO);
    }

    @Override
    public void myInitiateList() {

    }

    @Override
    public void withDrawlSpecific(Long tenantId, String flowKey, String businessKey) {
        workflowClient.flowWithdrawFlowKey(tenantId, flowKey, businessKey);
    }

    @Override
    public List<RunTaskHistory> getApprovedHistory(Long tenantId, String flowKey, String businessKey) {
        return workflowClient.approveHistoryByFlowKey(tenantId, flowKey, businessKey);
    }

    @Override
    public ProcessInstanceDTO.ProcessInstanceViewDTO getDetailsInstant(Long tenantId, String flowKey, String businessKey) {
        return workflowClient.getInstanceDetailByBusinessKey(tenantId, flowKey, businessKey);
    }

    @Override
    public void approve(Long tenantId, List<Long> tasksId, String comments) {
        workflowClient.approve(tenantId, tasksId, comments);
    }

    @Override
    public void reject(Long tenantId, List<Long> tasksId, String comments) {
        workflowClient.reject(tenantId, tasksId, comments);
    }
}