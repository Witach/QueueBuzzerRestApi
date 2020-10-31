package com.queuebuzzer.restapi.config;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Profile("dev")
@EnableWebSecurity
public class DevNoAuthConfig extends AuthConfig {
    @Override
    HttpSecurity makeHttpChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .anyRequest().permitAll();
        return httpSecurity;
    }
}
