package com.queuebuzzer.restapi.service;

import com.google.firebase.messaging.*;
import com.queuebuzzer.restapi.entity.ConsumerOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    FirebaseMessaging firebaseMessaging;

    public void notifyUserAboutOrder(ConsumerOrder order, String url) throws FirebaseMessagingException {
        Message message = Message.builder()
                .setToken(order.getFireBaseToken())
                .setNotification(Notification.builder()
                        .setTitle("Your order is done")
                        .setBody(String.format("Your order nr. %s is ready to be picked", order.getQueueNumber()))
                        .build())
                .setAndroidConfig(AndroidConfig.builder()
                        .setNotification(AndroidNotification.builder()
                                .setImage(url + "/logo.png")
                                .build())
                        .build())
                .setApnsConfig(ApnsConfig.builder()
                        .setAps(Aps.builder()
                                .setMutableContent(true)
                                .build())
                        .build())
                .setWebpushConfig(WebpushConfig.builder()
                        .putHeader("image", url + "/logo.png")
                        .build())
                .build();
        String response = firebaseMessaging.send(message);
    }
}
