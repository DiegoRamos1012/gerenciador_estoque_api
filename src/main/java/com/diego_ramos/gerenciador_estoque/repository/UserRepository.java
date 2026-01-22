package com.diego_ramos.gerenciador_estoque.repository;

import com.diego_ramos.gerenciador_estoque.domain.User;
import com.diego_ramos.gerenciador_estoque.enums.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    long countByRoleAndDeletedFalse(UserRole role);

    boolean existsByNameIgnoreCase(String name);

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByEmailIgnoreCaseAndDeletedFalse(String email);

    Optional<User> findByEmailIgnoreCaseAndDeletedFalse(String email);
}
