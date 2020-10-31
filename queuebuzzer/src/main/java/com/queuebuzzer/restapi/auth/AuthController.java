package com.queuebuzzer.restapi.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import static com.queuebuzzer.restapi.auth.AUthResponse.instanceFromUserDetails;
import static com.queuebuzzer.restapi.auth.JsonWebTokens.authTokenFromAuthRequest;


@Slf4j
@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService myUserDetailsService;

    @GetMapping
    public String hello(){
        return "Hello world";
    }


    @PostMapping
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
        authenticationManager.authenticate(authTokenFromAuthRequest(authenticationRequest));

        final var userDetails = myUserDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        return ResponseEntity.ok(instanceFromUserDetails(userDetails));
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(BadCredentialsException.class)
    void handleBadCredential(BadCredentialsException e) {
      e.printStackTrace();
    }
}
