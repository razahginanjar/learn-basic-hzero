package com.hand.demo.app.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.demo.domain.entity.Task;
import com.hand.demo.domain.repository.TaskRepository;
import com.hand.demo.domain.repository.UserRepository;
import com.hand.demo.infra.constant.Constants;
import io.choerodon.core.exception.CommonException;
import org.hzero.boot.imported.app.service.IDoImportService;
import org.hzero.boot.imported.app.service.ImportHandler;
import org.hzero.boot.imported.app.service.ValidatorHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

@ImportService(templateCode = "DEMO-CLIENT-47837")
public class ImportServiceImpl extends ImportHandler{
    private static final Logger log = LoggerFactory.getLogger(ImportServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final TaskRepository taskRepository;

    public ImportServiceImpl(ObjectMapper objectMapper, TaskRepository taskRepository) {
        this.objectMapper = objectMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public Boolean doImport(String data) {
        log.info(data);
        // 获取自定义参数
        Map<String, Object> args = getArgs();
        Task task;
        try {
            task = objectMapper.readValue(data, Task.class);
        } catch (IOException e) {
            // 记录错误信息
            getContext().addErrorMsg("这条数据有错");
            // 失败
            return false;
        }
        taskRepository.insertSelective(task);
        // 回写信息
        getContext().addBackInfo(String.valueOf(task.getId()));
        // 成功
        return true;
    }

}
