package com.hand.demo.app.service.impl;

import com.hand.demo.api.dto.MessageRequest;
import com.hand.demo.domain.entity.User;
import com.hand.demo.domain.repository.UserRepository;
import org.hzero.boot.message.MessageClient;
import org.hzero.boot.message.entity.FlyBookMsgType;
import org.hzero.boot.message.entity.Message;
import org.hzero.boot.message.entity.Receiver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {
    private static final Logger log = LoggerFactory.getLogger(MessageService.class);
    private final MessageClient messageClient;
    private final String TEMPLATE_CODE = "TEST-47837-FROM-CODING";
    private final String TEMPLATE_CODE_FEISHU = "TEST-FEISHU-47837";
    private final String LANGUAGE_CODE = "en_US";
    private final String SERVER_CODE = "TEST-47837";
    private final String SUBJECT = "FROM CODE";
    private final String SERVER_CODE_FEISHU = "FEIYU";
    private final UserRepository userRepository;

    public MessageService(MessageClient messageClient, UserRepository userRepository) {
        this.messageClient = messageClient;
        this.userRepository = userRepository;
    }

    public List<Message> sendNotification(Long tenantId, Long userId, List<String> message)
    {
        List<Message> responseMessages = new ArrayList<>();
        Receiver receiver = new Receiver();
        receiver.setUserId(userId);
        receiver.setTargetUserTenantId(tenantId);

        if(Objects.isNull(message))
        {

                Map<String, String> args = new HashMap<>();
                Message message1 = messageClient.sendWebMessage(tenantId, TEMPLATE_CODE, LANGUAGE_CODE,
                        Collections.singletonList(receiver),
                        args);
                responseMessages.add(message1);
        }else {
            for (String s : message) {
                Map<String, String> args = new HashMap<>();
                if(Objects.isNull(s))
                {
                    s = message.get(0);
                }
                args.put("msg", s);
                Message message1 = messageClient.sendWebMessage(tenantId, TEMPLATE_CODE, LANGUAGE_CODE,
                        Collections.singletonList(receiver),
                        args);
                responseMessages.add(message1);
            }
        }
        return responseMessages;
    }

    public void sendEmail(String context, String email, Long tenantId)
    {
        Receiver receiver = new Receiver();
        receiver.setEmail(email);
        receiver.setTargetUserTenantId(tenantId);

        messageClient.sendCustomEmail(
                tenantId,
                SERVER_CODE,
                SUBJECT,
                context,
                Collections.singletonList(receiver),
                null,
                null,
                null
                );
    }

    public Message sendFeishuMessage(
            long tenantId, String email, Map<String, String> map)
    {
        List<Map<String, String>> receivers = new ArrayList<>();
        Map<String, String> receiver = new HashMap<>();
        receiver.put("email", email);
        receivers.add(receiver);

        User user = userRepository.findByUserAccount(Long.valueOf(map.get("userId")));

        Map<String, Object> args = new HashMap<>();
        args.put("userName", user.getEmployeeName());
        args.put("empNumber", user.getEmployeeNumber());
        args.put("email", user.getEmail());

        return messageClient.sendFlyBook(tenantId,
                SERVER_CODE_FEISHU,
                TEMPLATE_CODE_FEISHU,
                FlyBookMsgType.TEXT,
                LANGUAGE_CODE,
                receivers,
                args
        );
    }
}
