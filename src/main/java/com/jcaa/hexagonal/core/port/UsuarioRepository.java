package com.jcaa.hexagonal.core.port;

import com.jcaa.hexagonal.core.domin.users.Email;
import com.jcaa.hexagonal.core.domin.users.UserAccount;
import com.jcaa.hexagonal.core.domin.users.UserId;
import com.jcaa.hexagonal.core.domin.users.UserName;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository {
    UserAccount save(UserAccount usuario);
    
    Optional<UserAccount> findById(UserId id);
    
    Optional<UserAccount> findByEmail(Email email);
    
    Optional<UserAccount> findByUserName(UserName userName);
    
    List<UserAccount> findAll();
    
    Page<UserAccount> findAll(Pageable pageable);
    
    void delete(UserId id);
    
    boolean existsByEmail(Email email);
    
    boolean existsByUserName(UserName userName);
    
    /**
     * Comprobación combinada de unicidad para email y nombre de usuario.
     */
    default boolean existsByEmailOrUserName(Email email, UserName userName) {
        return existsByEmail(email) || existsByUserName(userName);
    }
}

