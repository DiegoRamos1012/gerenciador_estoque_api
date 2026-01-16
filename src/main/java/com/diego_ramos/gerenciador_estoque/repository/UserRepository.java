package com.diego_ramos.gerenciador_estoque.repository;

import com.diego_ramos.gerenciador_estoque.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

}
