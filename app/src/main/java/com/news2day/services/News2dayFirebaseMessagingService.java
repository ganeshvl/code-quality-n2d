package com.news2day.services;

import android.os.Bundle;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


import java.util.Map;

public class News2dayFirebaseMessagingService extends FirebaseMessagingService {

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);

        try {


        } catch (Exception e) {


        }

    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String fromId = remoteMessage.getFrom();

        try {

            if (fromId != null && remoteMessage.getData().size() > 0) {

                Bundle extras = new Bundle();

                for (Map.Entry<String, String> entry : remoteMessage.getData().entrySet()) {

                    extras.putString(entry.getKey(), entry.getValue());

                }


                return;


            }

        } catch (Exception e) {


        }

    }

}
