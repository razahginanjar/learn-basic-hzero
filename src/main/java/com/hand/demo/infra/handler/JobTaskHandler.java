package com.hand.demo.infra.handler;

import com.hand.demo.app.service.impl.MessageService;
import com.hand.demo.domain.entity.User;
import com.hand.demo.domain.repository.UserRepository;
import org.hzero.boot.message.entity.Message;
import org.hzero.boot.scheduler.infra.annotation.JobHandler;
import org.hzero.boot.scheduler.infra.enums.ReturnT;
import org.hzero.boot.scheduler.infra.handler.IJobHandler;
import org.hzero.boot.scheduler.infra.tool.SchedulerTool;
import org.hzero.mybatis.domian.Condition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@JobHandler(value = "DEMO-47837")
public class JobTaskHandler implements IJobHandler {

    private static final Logger log = LoggerFactory.getLogger(JobTaskHandler.class);
    private final MessageService messageService;
    private final UserRepository userRepository;

    public JobTaskHandler(MessageService messageService, UserRepository userRepository) {
        this.messageService = messageService;
        this.userRepository = userRepository;
    }

    @Override
    public ReturnT execute(Map<String, String> map, SchedulerTool tool) {
        log.info("This is log service from: 47837 id: {}", map.get("userId"));

        String message = "to mr zeki this spam email from razah empId: 47837";

        String email = "shaoqin.zhou@hand-china.com";

        User user = userRepository.findByUserAccount(Long.valueOf(map.get("userId")));
        log.info("this is user account in long {}", map.get("userId"));
        Message message1 = messageService.sendFeishuMessage(0, email, user.getEmail(),
                user.getEmployeeNumber(),
                user.getEmployeeName());
        log.info("this message is: {}", message1);
        log.info("message from has been sent: {}", map.get("userId"));
        return ReturnT.SUCCESS;
    }

    @Override
    public void onFinish(SchedulerTool tool, ReturnT returnT) {
        IJobHandler.super.onFinish(tool, returnT);
    }
}
