package com.hand.demo.infra.handler;

import com.hand.demo.app.service.impl.MessageService;
import org.hzero.boot.message.entity.Message;
import org.hzero.boot.scheduler.infra.annotation.JobHandler;
import org.hzero.boot.scheduler.infra.enums.ReturnT;
import org.hzero.boot.scheduler.infra.handler.IJobHandler;
import org.hzero.boot.scheduler.infra.tool.SchedulerTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

@JobHandler(value = "DEMO-47837")
public class JobTaskHandler implements IJobHandler {

    private static final Logger log = LoggerFactory.getLogger(JobTaskHandler.class);
    private final MessageService messageService;

    public JobTaskHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public ReturnT execute(Map<String, String> map, SchedulerTool tool) {
        log.info("This is log service from: 47837 id: {}", map.get("userId"));

        String message = "to mr zeki this spam email from razah empId: 47837";

        String email = "shaoqin.zhou@hand-china.com";
        Message message1 = messageService.sendFeishuMessage(0, message, email);
        log.info("this message is: {}", message1);
        log.info("message from has been sent: {}", map.get("userId"));
        return ReturnT.SUCCESS;
    }

    @Override
    public void onFinish(SchedulerTool tool, ReturnT returnT) {
        IJobHandler.super.onFinish(tool, returnT);
    }
}
