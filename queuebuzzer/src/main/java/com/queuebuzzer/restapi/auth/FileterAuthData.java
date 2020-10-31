package com.queuebuzzer.restapi.auth;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static com.queuebuzzer.restapi.auth.JwtUtils.extractUsername;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileterAuthData {
    private String username = null;
    private String jwt = null;

    public FileterAuthData(String jwt) {
        this.username = extractUsername(jwt);
        this.jwt = jwt;
    }
}
