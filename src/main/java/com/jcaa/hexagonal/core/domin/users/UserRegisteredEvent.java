package com.jcaa.hexagonal.core.domin.users;

import com.jcaa.hexagonal.core.domin.DomainEvent;

import java.time.LocalDateTime;

public class UserRegisteredEvent implements DomainEvent {
    private final UserId userId;
    private final UserName userName;
    private final Email email;
    private final Role role;
    private final LocalDateTime occurredOn;
    
    public UserRegisteredEvent(UserId userId, UserName userName, Email email, Role role) {
        this(userId, userName, email, role, LocalDateTime.now());
    }
    
    public UserRegisteredEvent(UserId userId, UserName userName, Email email, Role role, LocalDateTime occurredOn) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.role = role;
        this.occurredOn = occurredOn;
    }
    
    public UserId getUserId() {
        return userId;
    }
    
    public UserName getUserName() {
        return userName;
    }
    
    public Email getEmail() {
        return email;
    }
    
    public Role getRole() {
        return role;
    }
    
    @Override
    public LocalDateTime occurredOn() {
        return occurredOn;
    }
}
