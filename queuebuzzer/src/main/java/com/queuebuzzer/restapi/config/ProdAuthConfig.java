package com.queuebuzzer.restapi.config;

import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Profile({"devauth", "prod"})
@EnableWebSecurity
public class ProdAuthConfig extends AuthConfig {
    @Override
    HttpSecurity makeHttpChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeRequests()
                .antMatchers(IGNORED_URLS.toArray(String[]::new)).permitAll()
                .antMatchers("/auth").permitAll()
                .antMatchers("/consumer-order").permitAll()
                .antMatchers(HttpMethod.GET ,"/consumer").permitAll()
                .antMatchers(HttpMethod.GET, "/point").permitAll()
                .antMatchers(HttpMethod.GET, "/product").permitAll()
                .antMatchers(HttpMethod.GET, "/state").permitAll()
                .anyRequest().authenticated();
        return httpSecurity;
    }
}
