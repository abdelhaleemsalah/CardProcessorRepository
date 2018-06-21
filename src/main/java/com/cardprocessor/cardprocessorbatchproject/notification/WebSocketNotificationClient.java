package com.cardprocessor.cardprocessorbatchproject.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class WebSocketNotificationClient {


    @Autowired
    SimpMessagingTemplate simpMessagingTemplate;

    public void sendNotificationToUser(String message)
    {
        simpMessagingTemplate.setDefaultDestination("/topic/notification");
        simpMessagingTemplate.convertAndSend(message);
    }
}
