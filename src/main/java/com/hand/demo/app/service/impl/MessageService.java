package com.hand.demo.app.service.impl;

import com.hand.demo.api.dto.MessageRequest;
import org.hzero.boot.message.MessageClient;
import org.hzero.boot.message.entity.Message;
import org.hzero.boot.message.entity.Receiver;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MessageService {
    private final MessageClient messageClient;
    private final String TEMPLATE_CODE = "TEST-47837-FROM-CODING";
    private final String LANGUAGE_CODE = "en_US";
    private final String SERVER_CODE = "TEST-47837";
    private final String SUBJECT = "FROM CODE";

    public MessageService(MessageClient messageClient) {
        this.messageClient = messageClient;
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

}
