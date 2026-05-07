package com.brewery.web.services;

import com.brewery.web.model.User;
import com.brewery.web.model.UserCredential;
import com.brewery.web.repositories.UserCredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class UserCredentialService {
    @Autowired
    private UserCredentialRepository userCredentialRepository;

    public UserCredential getUserCredentialByUserId(UUID userId) {
        return this.userCredentialRepository.getUserCredentialByUserId(userId);
    }

    public UserCredential saveUserCredentialByUser(User user) {
        UserCredential credential = new UserCredential();
        Instant now = Instant.now();

        user.setUserId(UUID.randomUUID());
        credential.setId(UUID.randomUUID());
        credential.setCreateDate(now);
        credential.setUpdateDate(now);
        credential.setStatus("Active");
        credential.setUserId(user.getUserId());

        return this.userCredentialRepository.save(credential);
    }

}
