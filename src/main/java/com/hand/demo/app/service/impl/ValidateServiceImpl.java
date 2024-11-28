package com.hand.demo.app.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hand.demo.domain.entity.Task;
import com.hand.demo.domain.repository.TaskRepository;
import com.hand.demo.domain.repository.UserRepository;
import com.hand.demo.infra.constant.Constants;
import io.choerodon.core.exception.CommonException;
import org.hzero.boot.imported.app.service.ValidatorHandler;
import org.hzero.boot.imported.infra.validator.annotation.ImportService;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidator;
import org.hzero.boot.imported.infra.validator.annotation.ImportValidators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ImportValidators(
        {
                @ImportValidator(templateCode = "DEMO-CLIENT-47837")
        }
)
public class ValidateServiceImpl extends ValidatorHandler {
    private static final Logger log = LoggerFactory.getLogger(ValidateServiceImpl.class);
    private final ObjectMapper objectMapper;
    private final UserRepository userRepository;

    public ValidateServiceImpl(ObjectMapper objectMapper, UserRepository userRepository) {
        this.objectMapper = objectMapper;
        this.userRepository = userRepository;
    }

    @Override
    public boolean validate(String data) {
        Task task;
        try{
            task = objectMapper.readValue(data, Task.class);
            Long employeeId = task.getEmployeeId();
            if(!userRepository.existsWithPrimaryKey(employeeId))
            {
                throw new CommonException("Employee Id do not exist");
            }
            String taskNumber = task.getTaskNumber();
            if(!taskNumber.matches(Constants.REGEX_EN_US))
            {
                throw new CommonException("Task Number is invalid");
            }
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
        return true;
    }
}
