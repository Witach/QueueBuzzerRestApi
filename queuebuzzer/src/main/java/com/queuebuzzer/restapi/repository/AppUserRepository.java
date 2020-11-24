package com.queuebuzzer.restapi.repository;

import com.queuebuzzer.restapi.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    Optional<AppUser> findByEmial(String emial);
}
