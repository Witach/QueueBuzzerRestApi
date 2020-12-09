package com.queuebuzzer.restapi.config;

import com.github.javafaker.Faker;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.FirebaseMessaging;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Locale;

@Configuration
public class BeanInitializer {
    @Bean
    public Faker faker() {
        return new Faker(new Locale("pl", "PL"));
    }

    @Bean
    public FirebaseMessaging firebaseMessaging() throws IOException {
        FileInputStream serviceAccount = new FileInputStream("queuebuzzer-b73c6-firebase-adminsdk-z7776-21ae52c6f8.json");
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://queuebuzzer-b73c6.firebaseio.com")
                .build();
        FirebaseApp.initializeApp(options);
        return FirebaseMessaging.getInstance();
    }
}
