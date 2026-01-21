// java
package com.diego_ramos.gerenciador_estoque.domain;

import com.diego_ramos.gerenciador_estoque.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static com.diego_ramos.gerenciador_estoque.utils.Validators.*;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE users SET deleted = true, deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@Entity
@Table(name = "users")
public class User extends BaseEntity implements UserDetails {

    @Column(nullable = false, unique = true)
    private String email;

    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Column(name = "password_hash", nullable = false)
    private String passwordHash;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role;

    @Column(nullable = false)
    private boolean deleted = false;

    @Column(name = "deleted_at")
    private Instant deletedAt;

    /* Construtor interno */
    private User(String name,
                 String email,
                 String passwordHash,
                 UserRole role) {

        email = email.toLowerCase();

        validateName(name);
        validateEmail(email);
        validatePassword(passwordHash);
        validateRole(role);

        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    /* Factory */
    public static User create(String name,
                              String email,
                              String passwordHash) {

        return new User(
                name,
                email,
                passwordHash,
                UserRole.EMPLOYEE
        );
    }

    public void changeName(String newName) {
        validateName(newName);
        this.name = newName;
    }

    public void changeEmail(String newEmail) {
        newEmail = newEmail.toLowerCase();
        validateEmail(newEmail);
        this.email = newEmail;
    }

    public void changePassword(String newPasswordHash) {
        if (newPasswordHash == null) {
            throw new IllegalStateException("Hash de senha inválido");
        }
        this.passwordHash = newPasswordHash;
    }

    public void changeRole(UserRole newRole) {
        validateRole(newRole);
        this.role = newRole;
    }

    public void softDelete() {
        if (this.deleted) {
            return;
        }
        this.deleted = true;
        this.deletedAt = Instant.now();
    }

    public void restore() {
        if (!this.deleted) {
            return;
        }
        this.deleted = false;
        this.deletedAt = null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public @Nullable String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    /* Campos herdados de BaseEntity:
     * - id
     * - name
     * - createdAt
     * - lastTimeChanged
     */
}
