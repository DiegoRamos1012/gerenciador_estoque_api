// java
package com.diego_ramos.gerenciador_estoque.domain;

import com.diego_ramos.gerenciador_estoque.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;

import static com.diego_ramos.gerenciador_estoque.utils.Validators.*;

/**
 * Representa um usuário do sistema.
 * <p>
 * Herda campos e comportamento de BaseEntity:
 * - id
 * - name
 * - createdAt
 * - lastTimeChanged
 */

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

    // Verificar se vale a pena inserir coluna "Status" para gerenciar se o usuário está ativo ou de férias

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

        validateUserName(name);
        validateEmail(email);
        validatePassword(passwordHash);
        validateRole(role);

        this.name = name;
        this.email = email;
        this.passwordHash = passwordHash;
        this.role = role;
    }

    /* Factory (alinhada com Product.create) */
    public static @NonNull User create(String name,
                                       String email,
                                       String passwordHash) {

        return new User(
                name,
                email,
                passwordHash,
                UserRole.EMPLOYEE
        );
    }

    /**
     * Atualiza os principais atributos do usuário.
     *
     * @param name         Novo nome
     * @param email        Novo email
     * @param passwordHash Novo passwordHash
     * @param newRole      Novo papel (pode ser null para não alterar)
     */
    public void update(String name, String email, String passwordHash, UserRole newRole) {
        String normalizedEmail = email.toLowerCase();

        validateUserName(name);
        validateEmail(normalizedEmail);
        validatePassword(passwordHash);

        this.name = name;
        this.email = normalizedEmail;
        this.passwordHash = passwordHash;

        if (newRole != null) {
            changeRole(newRole);
        }

        updateLastTimeChanged();
    }

    public void changeName(String newName) {
        validateUserName(newName);
        this.name = newName;
        updateLastTimeChanged();
    }

    public void changeEmail(String newEmail) {
        newEmail = newEmail.toLowerCase();
        validateEmail(newEmail);
        this.email = newEmail;
        updateLastTimeChanged();
    }

    public void changePassword(String newPasswordHash) {
        validatePassword(newPasswordHash);
        this.passwordHash = newPasswordHash;
        updateLastTimeChanged();
    }

    public void changeRole(UserRole newRole) {
        validateRole(newRole);
        this.role = newRole;
        updateLastTimeChanged();
    }

    // Presume-se que BaseEntity contenha os campos deleted e deletedAt (como em Product)
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
    public @NonNull Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(
                new SimpleGrantedAuthority("ROLE_" + role.name())
        );
    }

    @Override
    public @Nullable String getPassword() {
        return passwordHash;
    }

    @Override
    public @NonNull String getUsername() {
        return email;
    }

    // Implementações padrão do UserDetails
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return !this.deleted;
    }

    @Override
    protected void updateLastTimeChanged() {
        super.updateLastTimeChanged();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
