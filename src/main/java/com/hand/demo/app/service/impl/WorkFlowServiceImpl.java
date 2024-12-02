package com.hand.demo.app.service.impl;

import com.hand.demo.api.dto.WorkFlowRequestDTO;
import com.hand.demo.app.service.WorkFlowService;
import com.hand.demo.domain.entity.InvCountHeader;
import io.choerodon.core.domain.Page;
import io.choerodon.core.exception.CommonException;
import org.hzero.boot.workflow.WorkflowClient;
import org.hzero.boot.workflow.dto.PersonalTodoDTO;
import org.hzero.boot.workflow.dto.ProcessInstanceDTO;
import org.hzero.boot.workflow.dto.RunInstance;
import org.hzero.boot.workflow.dto.RunTaskHistory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class WorkFlowServiceImpl implements WorkFlowService {

    private final WorkflowClient workflowClient;
    private final InvCountHeaderServiceImpl invCountHeaderService;

    public WorkFlowServiceImpl(WorkflowClient workflowClient, InvCountHeaderServiceImpl invCountHeaderService) {
        this.workflowClient = workflowClient;
        this.invCountHeaderService = invCountHeaderService;
    }

    @Override
    public RunInstance start(Long tenantId, WorkFlowRequestDTO request) {
        InvCountHeader byCountNumber = invCountHeaderService.getByCountNumber(request.getBusinessKey());

        if (Objects.isNull(byCountNumber)) {
            throw new CommonException("demo-47837.inv_header_not_found.error", request.getBusinessKey());
        }
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