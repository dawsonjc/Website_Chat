package com.brewery.web.services;

import com.brewery.web.model.UserRole;
import com.brewery.web.repositories.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class UserRolesService {

    @Autowired
    private UserRoleRepository repo;

    public List<String> getUsersRolesByUserId(UUID userId) {
        List<UserRole> roles = this.repo.getAllRoleByUserId(userId);

        List<String> roleNames = new ArrayList<String>(roles.size());

        for(UserRole userRole : roles) {
            roleNames.add(userRole.getRoleName());
        }

        return roleNames;
    }

    public void save(UserRole userRole) {
        this.repo.save(userRole);
    }
}
