package com.queuebuzzer.restapi.auth;

import com.queuebuzzer.restapi.entity.AppUser;
import com.queuebuzzer.restapi.repository.AppUserRepository;
import com.queuebuzzer.restapi.repository.PointOwnerRepository;
import com.queuebuzzer.restapi.service.PointOwnerService;
import com.queuebuzzer.restapi.utils.EntityDoesNotExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AppUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        var user = repository.findByEmial(username)
                .orElseThrow(
                        () -> new EntityDoesNotExistsException(String.format(PointOwnerService.EXCPETION_PATTERN_STRING, username))
                );

        if (user == null) {
            throw new UsernameNotFoundException(username);
        }
        return new User(user.getEmial(), user.getPassword(), List.of(() -> "DEFAULT"));
    }
}
