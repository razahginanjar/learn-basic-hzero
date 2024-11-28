package com.hand.demo.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.demo.domain.entity.Task;
import com.hand.demo.domain.repository.TaskRepository;
import com.hand.demo.domain.repository.UserRepository;
import com.hand.demo.infra.constant.Constants;
import io.choerodon.core.exception.CommonException;
import org.hzero.boot.imported.app.service.BatchImportHandler;
import org.hzero.boot.imported.app.service.IBatchImportService;
import org.hzero.boot.imported.infra.validator.annotation.ImportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@ImportService(templateCode = "DEMO-CLIENT-47837")
public class BatchImportServiceImpl extends BatchImportHandler {

    private static final Logger log = LoggerFactory.getLogger(BatchImportServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final TaskRepository taskRepository;

    public BatchImportServiceImpl(ObjectMapper objectMapper, TaskRepository taskRepository) {
        this.objectMapper = objectMapper;
        this.taskRepository = taskRepository;
    }

    @Override
    public Boolean doImport(List<String> data) {
        for (String datum : data) {
            log.debug(datum);
        }
        // 获取自定义参数
        Map<String, Object> args = getArgs();
        Task task;
        for (String datum : data) {
            try {
                task = objectMapper.readValue(datum, Task.class);
                taskRepository.insertSelective(task);
            } catch (IOException e) {
                // 失败
                log.error(e.getMessage());
                return false;
            }
            // 回写信息
            log.info("This is the Id of the data in database {}", task.getId());
        }
        // 成功
        return true;
    }

    @Override
    public int getSize() {
        return 600;
    }
}
