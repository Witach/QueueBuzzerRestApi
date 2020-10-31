package com.queuebuzzer.restapi.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

public class JsonWebTokens {
    public static UsernamePasswordAuthenticationToken authTokenDataInstance(UserDetails userDetails) {
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }

    public static UsernamePasswordAuthenticationToken authTokenFromAuthRequest(AuthRequest authRequest) {
        return new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());
    }
}
