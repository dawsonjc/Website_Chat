package com.brewery.web.services;

import com.brewery.web.dto.ConversationDTO;
import com.brewery.web.model.Role;
import com.brewery.web.model.User;
import com.brewery.web.model.UserRole;
import com.brewery.web.repositories.UserTableRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service(value = "UserTableService")
public class UserTableService {

    @Autowired
    private UserTableRepository repo;

    @Autowired
    private UserRolesService userRolesService;

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private RoleService roleService;

    public boolean userExists(String email) {
        UUID userId = this.getUserIdByEmail(email);
        return userId != null;
    }

    public User getUserById(UUID id) {
        User user = this.repo.findById(id).orElse(null);
        if(user != null) {
            this.populateUserRelations(user);
        }
        return user;
    }

    public UUID getUserIdByEmail(String email) {
        return this.repo.findUserIdByEmail(email);
    }

    public User getUserByIdAndPassword(UUID id, String password) {
        User user = this.repo.findById(id).orElse(null);
        if(user == null) {
            return null;
        }

        if(!BCrypt.checkpw(password, user.getPassword())) {
            return null;
        }
        this.populateUserRelations(user);

        return user;
    }

    private void populateUserRelations(User user) {
        user.setRoles(this.getUsersRolesByUser(user));
        List<ConversationDTO> conversations = this.conversationService.getConversationsByUserId(user.getUserId());
        conversations.add(this.conversationService.getGlobalChat());

        user.setConversations(conversations);
    }

    public List<String> getUsersRolesByUser(User user) {
        return this.userRolesService.getUsersRolesByUserId(user.getUserId());
    }

    public List<User> getUnverifiedUsers() {
        return this.repo.getUnverifiedUsers();
    }

    public User register(User user) {
        Instant now = Instant.now();

        String hashed = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(10));

        UUID userId = UUID.randomUUID();

        user.setUserId(userId);
        user.setCreateDate(now);
        user.setUpdateDate(now);
        user.setStatus("Active");
        user.setPassword(hashed);
        user.setFullName(user.getFirstName() + " " + user.getLastName());
        user.setLanguagePreference("en");
        user.setTimezone("en/us");
        user.setAccountVerificationStatus("Unverified");

        Role defaultRole = this.roleService.getRoleByName("User");

        UserRole userRole = new UserRole();
        userRole.setRoleId(defaultRole.getRoleId());
        userRole.setRoleName(defaultRole.getRoleName());
        userRole.setUserId(userId);
        userRole.setCreateDate(now);
        userRole.setUpdateDate(now);
        userRole.setStatus("Active");

        this.userRolesService.save(userRole);

        return this.repo.save(user);
    }

    public void save(User user) {
        this.repo.save(user);
    }
}
