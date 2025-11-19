package com.jcaa.hexagonal.core.domin.users;

import com.jcaa.hexagonal.core.domin.AggregateRoot;

import java.time.LocalDateTime;
import java.util.Objects;

public class UserAccount extends AggregateRoot {
    private UserId id;
    private UserName userName;
    private Email email;
    private PasswordHash passwordHash;
    private Role role;
    private boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Constructor privado para JPA
    private UserAccount() {
    }
    
    public UserAccount(UserId id, UserName userName, Email email, PasswordHash passwordHash, Role role) {
        this.id = id;
        this.userName = userName;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
        this.isActive = true;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = null;
        
        addDomainEvent(new UserRegisteredEvent(id, userName, email, role));
    }
    
    public static UserAccount create(UserName userName, Email email, PasswordHash passwordHash, Role role) {
        return new UserAccount(UserId.newId(), userName, email, passwordHash, role);
    }
    
    // Método público para reconstrucción desde BD (sin eventos)
    public static UserAccount reconstitute(
            UserId id,
            UserName userName,
            Email email,
            PasswordHash passwordHash,
            Role role,
            boolean isActive,
            LocalDateTime createdAt,
            LocalDateTime updatedAt) {
        UserAccount account = new UserAccount();
        account.id = id;
        account.userName = userName;
        account.email = email;
        account.passwordHash = passwordHash;
        account.role = role;
        account.isActive = isActive;
        account.createdAt = createdAt;
        account.updatedAt = updatedAt;
        return account;
    }
    
    private void markUpdated() {
        this.updatedAt = LocalDateTime.now();
    }
    
    public void changePassword(PasswordHash newPasswordHash) {
        this.passwordHash = newPasswordHash;
        markUpdated();
    }
    
    public void rename(UserName newUserName) {
        if (this.userName.equals(newUserName)) {
            return;
        }
        this.userName = newUserName;
        markUpdated();
    }
    
    public void assignRole(Role newRole) {
        if (this.role.equals(newRole)) {
            return;
        }
        this.role = newRole;
        markUpdated();
    }
    
    public void deactivate() {
        if (!isActive) {
            throw new IllegalStateException("El usuario ya está inactivo");
        }
        this.isActive = false;
        markUpdated();
    }
    
    public void reactivate() {
        if (isActive) {
            throw new IllegalStateException("El usuario ya está activo");
        }
        this.isActive = true;
        markUpdated();
    }
    
    public void updateEmail(Email newEmail) {
        this.email = newEmail;
        markUpdated();
    }
    
    public boolean isInactive() {
        return !isActive;
    }
    
    // Getters
    public UserId getId() {
        return id;
    }
    
    public UserName getUserName() {
        return userName;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public PasswordHash getPasswordHash() {
        return passwordHash;
    }
    
    public Role getRole() {
        return role;
    }
    
    public boolean isActive() {
        return isActive;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        UserAccount other = (UserAccount) obj;
        return Objects.equals(id, other.id);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

