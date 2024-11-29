package com.hand.demo.app.service;

import com.hand.demo.api.dto.WorkFlowRequestDTO;
import io.choerodon.core.domain.Page;
import org.hzero.boot.workflow.dto.PersonalTodoDTO;
import org.hzero.boot.workflow.dto.ProcessInstanceDTO;
import org.hzero.boot.workflow.dto.RunInstance;
import org.hzero.boot.workflow.dto.RunTaskHistory;

import java.util.List;

public interface WorkFlowService {
    RunInstance start(Long tenantId, WorkFlowRequestDTO request);
    Page<PersonalTodoDTO.PersonalTodoViewDTO> myTodoList(Long tenantId, Long page, Long size, String flowName);
    void myInitiateList();
    void withDrawlSpecific(Long tenantId, String flowKey, String businessKey);
    List<RunTaskHistory> getApprovedHistory(Long tenantId, String flowKey, String businessKey);
    ProcessInstanceDTO.ProcessInstanceViewDTO getDetailsInstant(Long tenantId, String flowKey, String businessKey);
    void approve(Long tenantId, List<Long> tasksId, String comments);
    void reject(Long tenantId, List<Long> tasksId, String comments);
}
