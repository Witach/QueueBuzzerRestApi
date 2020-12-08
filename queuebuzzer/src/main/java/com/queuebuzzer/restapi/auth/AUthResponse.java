package com.queuebuzzer.restapi.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;

import static com.queuebuzzer.restapi.auth.JwtUtils.generateToken;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AUthResponse {
    private String jwt;
    private String userType;

    public static AUthResponse instanceFromUserDetails(UserDetails userDetails) {
        return AUthResponse.builder()
                .jwt(generateToken(userDetails))
                .build();
    }
}
