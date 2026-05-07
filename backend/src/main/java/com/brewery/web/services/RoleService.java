package com.brewery.web.services;

import com.brewery.web.model.Role;
import com.brewery.web.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public Role getRoleByName(String name) {
        return this.roleRepository.getRoleByName(name);
    }

    public List<Role> getActiveRoles() {
        return this.roleRepository.getActiveRoles();
    }
}
