package com.jcaa.hexagonal.core.domin.users;

import com.jcaa.hexagonal.core.domin.ValueObject;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class Role extends ValueObject {
    private final String value;
    
    private static final Set<String> VALID_ROLES = Set.of("Admin", "User", "Guest");
    public static final Role ADMIN = new Role("Admin");
    public static final Role USER = new Role("User");
    public static final Role GUEST = new Role("Guest");
    
    public Role(String value) {
        String normalizedRole = normalize(value);
        validateRole(normalizedRole, value);
        this.value = normalizedRole;
    }
    
    private static String normalize(String raw) {
        if (raw == null) {
            throw new IllegalArgumentException("El rol no puede estar vacío");
        }
        String trimmed = raw.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("El rol no puede estar vacío");
        }
        return trimmed;
    }
    
    private static void validateRole(String normalized, String original) {
        if (!VALID_ROLES.contains(normalized)) {
            throw new IllegalArgumentException(
                    String.format("El rol '%s' no es válido. Roles válidos: %s",
                            original, String.join(", ", VALID_ROLES))
            );
        }
    }
    
    public String getValue() {
        return value;
    }
    
    public boolean isAdmin() {
        return ADMIN.equals(this);
    }
    
    public boolean isUser() {
        return USER.equals(this);
    }
    
    public boolean isGuest() {
        return GUEST.equals(this);
    }
    
    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(value);
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Role role = (Role) obj;
        return Objects.equals(value, role.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
