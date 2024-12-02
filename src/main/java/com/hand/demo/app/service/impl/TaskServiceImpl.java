package com.hand.demo.app.service.impl;

import com.hand.demo.app.service.TaskService;
import com.hand.demo.domain.entity.Task;
import com.hand.demo.domain.repository.TaskRepository;
import com.hand.demo.infra.constant.ConstantLovCode;
import com.hand.demo.infra.constant.ConstantRoleCode;
import io.choerodon.core.exception.CommonException;
import io.choerodon.core.oauth.DetailsHelper;
import lombok.extern.slf4j.Slf4j;
import org.hzero.boot.platform.code.builder.CodeRuleBuilder;
import org.hzero.boot.platform.lov.adapter.LovAdapter;
import org.hzero.boot.platform.lov.dto.LovValueDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class TaskServiceImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CodeRuleBuilder codeRuleBuilder;

    @Autowired
    private LovAdapter lovAdapter;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Task create(Task task) {
        // 生成任务编号
        List<LovValueDTO> lovValueTaskStatus =
                lovAdapter.queryLovValue(ConstantLovCode.LOV_CODE_47837_2, task.getTenantId());
//        lovAdapter.
//        lovAdapter.queryLovValue()
        for (LovValueDTO valueTaskStatus : lovValueTaskStatus) {
            if(valueTaskStatus.getValue().equals(task.getState()))
            {
                List<LovValueDTO> lovValueTaskType =
                        lovAdapter.queryLovValue(ConstantLovCode.LOV_CODE_47837, task.getTenantId());
                for (LovValueDTO lovValueDTO : lovValueTaskType) {
                    if(lovValueDTO.getValue().equals(task.getTaskType()))
                    {
                        Map<String, String> args = new HashMap<>();
                        String realName = DetailsHelper.getUserDetails().getRealName();
                        args.put("customSegment", realName + "-");
                        String s = codeRuleBuilder.generateCode(ConstantRoleCode.CODE_ROLE, args);
                        task.setTaskNumber(s);
                        // 插入数据
                        taskRepository.insert(task);
                        return task;
                    }
                }
                throw new CommonException("demo-47837.task_type_different.error", task.getTaskType());
            }
        }
        throw new CommonException("demo-47837.task_state_deifferent.error", task.getState());
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Task update(Task task) {
        Task exist = taskRepository.selectByPrimaryKey(task);
        if (exist == null) {
            throw new CommonException("htdo.warn.task.notFound");
        }
        // 更新指定字段
        taskRepository.updateOptional(task,
                Task.FIELD_STATE,
                Task.FIELD_TASK_DESCRIPTION
        );
        return task;
    }
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteByTaskNumber(String taskNumber) {
        Task task = new Task();
        task.setTaskNumber(taskNumber);
        taskRepository.delete(task);
    }

    @Override
    public List<Task> findAll() {
        return Collections.emptyList();
    }
}
