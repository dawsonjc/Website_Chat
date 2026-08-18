package com.brewery.web.model;

import com.brewery.web.dto.ConversationDTO;
import com.brewery.web.model.record.RecordStatus;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;



@Table(value = "user_table")
public class User {

    @Id
    @Column(value = "userid")
    @PrimaryKeyColumn(name = "userid", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private UUID userId;

    @Column(value = "create_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant createDate;

    @Column(value = "update_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant updateDate;

    @Column(value = "status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private RecordStatus status;

    @Column(value = "username")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String username;

    @Column(value = "email")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String email;

    @Column(value = "password")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String password;

    @Column(value = "first_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String firstName;

    @Column(value = "last_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String lastName;

    @Column(value = "full_name")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String fullName;

    @Column(value = "language_preference")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String languagePreference;

    @Column(value = "timezone")
    @CassandraType(type = CassandraType.Name.TEXT)
    private String timezone;

    @Column(value = "last_login_date")
    @CassandraType(type = CassandraType.Name.TIMESTAMP)
    private Instant lastLoginDate;

    @Column(value = "account_verification_status")
    @CassandraType(type = CassandraType.Name.TEXT)
    private VerificationStatus accountVerificationStatus;

    @Column(value = "two_factor_authentication")
    @CassandraType(type = CassandraType.Name.BOOLEAN)
    private Boolean twoFactorAuthentication;

    @Column(value = "friends_connections")
    @CassandraType(type = CassandraType.Name.SET, typeArguments = { CassandraType.Name.UUID })
    private Set<UUID> friendsConnections;

    @Column(value = "blocked_users")
    @CassandraType(type = CassandraType.Name.SET, typeArguments = { CassandraType.Name.UUID })
    private Set<UUID> blockedUsers;
    
    @Column(value = "api_auth_token")
    @CassandraType(type = CassandraType.Name.UUID)
    private UUID apiAuthToken;

    @Transient
    private List<String> roles;

    @Transient
    private List<ConversationDTO> conversations;

    public User() {}

    public User(UUID userId, Instant createDate, Instant updateDate, RecordStatus status,
                String username, String email, String password, String firstName,
                String lastName, String fullName,
                String languagePreference, String timezone, Instant lastLoginDate,
                VerificationStatus accountVerificationStatus, Boolean twoFactorAuthentication,
                Set<UUID> friendsConnections, Set<UUID> blockedUsers, UUID apiAuthToken
    ) {
        this.userId = userId;
        this.createDate = createDate;
        this.updateDate = updateDate;
        this.status = status;
        this.username = username;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.languagePreference = languagePreference;
        this.timezone = timezone;
        this.lastLoginDate = lastLoginDate;
        this.accountVerificationStatus = accountVerificationStatus;
        this.twoFactorAuthentication = twoFactorAuthentication;
        this.friendsConnections = friendsConnections;
        this.blockedUsers = blockedUsers;
        this.apiAuthToken = apiAuthToken;
    }

    public enum VerificationStatus {
        PENDING("Pending"),
        VERIFIED("Verified"),
        BLOCKED("Blocked");

        private final String status;
        VerificationStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return this.status;
        }

        public static VerificationStatus getStatus(String status) {
            for (VerificationStatus s : VerificationStatus.values()) {
                if (s.getStatus().equals(status)) {
                    return s;
                }
            }
            return null;
        }

        public String toString() {
            return this.status;
        }
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public Instant getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Instant createDate) {
        this.createDate = createDate;
    }

    public Instant getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public RecordStatus getStatus() {
        return status;
    }

    public void setStatus(RecordStatus status) {
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getLanguagePreference() {
        return languagePreference;
    }

    public void setLanguagePreference(String languagePreference) {
        this.languagePreference = languagePreference;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Instant getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Instant lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public VerificationStatus getAccountVerificationStatus() {
        return accountVerificationStatus;
    }

    public void setAccountVerificationStatus(VerificationStatus accountVerificationStatus) {
        this.accountVerificationStatus = accountVerificationStatus;
    }

    public Boolean isTwoFactorAuthentication() {
        return twoFactorAuthentication;
    }

    public void setTwoFactorAuthentication(Boolean twoFactorAuthentication) {
        this.twoFactorAuthentication = twoFactorAuthentication;
    }

    public Set<UUID> getFriendsConnections() {
        return friendsConnections;
    }

    public void setFriendsConnections(Set<UUID> friendsConnections) {
        this.friendsConnections = friendsConnections;
    }

    public Set<UUID> getBlockedUsers() {
        return blockedUsers;
    }

    public void setBlockedUsers(Set<UUID> blockedUsers) {
        this.blockedUsers = blockedUsers;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getRoles() {
        return this.roles;
    }

    public void setConversations(List<ConversationDTO> conversations) {
        this.conversations = conversations;
    }

    public List<ConversationDTO> getConversations() {
        return this.conversations;
    }

    public UUID getApiAuthToken() {
        return this.apiAuthToken;
    }

    public void setApiAuthToken(UUID apiAuthToken) {
        this.apiAuthToken = apiAuthToken;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", createDate=" + createDate +
                ", updateDate=" + updateDate +
                ", status='" + status + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", fullName='" + fullName + '\'' +
                ", languagePreference='" + languagePreference + '\'' +
                ", timezone='" + timezone + '\'' +
                ", lastLoginDate=" + lastLoginDate +
                ", accountVerificationStatus='" + accountVerificationStatus + '\'' +
                ", twoFactorAuthentication=" + twoFactorAuthentication +
                ", friendsConnections=" + friendsConnections +
                ", blockedUsers=" + blockedUsers +
                '}';
    }
}
