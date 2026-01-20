package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.domain.User;
import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserUpdateDTO;
import com.diego_ramos.gerenciador_estoque.enums.UserRole;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
import com.diego_ramos.gerenciador_estoque.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;


@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void register(UserCreateDTO dto) {
        if (userRepository.existsByNameIgnoreCase(dto.name())) {
            throw new BusinessException("Já existe um usuário com esse nome");
        }

        if (userRepository.existsByEmailIgnoreCase(dto.email())) {
            throw new BusinessException("Já existe um usuário com esse email");
        }

        String passwordHash = passwordEncoder.encode(dto.password());

        User user = User.create(dto.name(), dto.email(), passwordHash);

        userRepository.save(user);
    }

    public void changeName(UUID id, @NonNull UserUpdateDTO dto) {
        User user = userRepository.findById(dto.id()).
                orElseThrow(() -> new BusinessException(("Usuário não encontrado")));

        if (dto.name() == null) {
            throw new BusinessException(("Nome não informado pra alteração"));
        }

        String newName = dto.name();

        if (userRepository.existsByNameIgnoreCase(newName) &&
                !user.getName().equalsIgnoreCase(newName)) {
            throw new BusinessException("Já existe um usuário com esse nome");
        }

        user.changeName(newName);
        userRepository.save(user);
    }

    public void changeEmail(UUID id, @NonNull UserUpdateDTO dto) {
        User user = userRepository.findById(dto.id()).
                orElseThrow(() -> new BusinessException(("Usuário não encontrado")));

        if (dto.email() == null) {
            throw new BusinessException(("Email não informado pra alteração"));
        }

        String newEmail = dto.email();

        if (!user.getEmail().equalsIgnoreCase(newEmail)
                && userRepository.existsByEmailIgnoreCase(newEmail)) {
            throw new BusinessException("Já existe um usuário cadastrado com esse email");
        }

        user.changeEmail(newEmail);

        userRepository.save(user);
    }

    public void changePassword(UUID id, @NonNull UserUpdateDTO dto) {
        User user = userRepository.findById(dto.id())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        if (dto.password() == null) {
            throw new BusinessException("Senha não informada para alteração");
        }

        String rawPassword = dto.password();

        String newPasswordHash = passwordEncoder.encode(rawPassword);

        user.changePassword(newPasswordHash);

        userRepository.save(user);
    }

    public void changeRole(UUID id, @NonNull UserUpdateDTO dto) {
        User user = userRepository.findById(dto.id())
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        if (dto.role() == null) {
            throw new BusinessException("Cargo não informado para alteração");
        }

        UserRole newRole = dto.role();
        UserRole currentRole = user.getRole();

        // evita operação inútil
        if (currentRole == newRole) {
            throw new BusinessException("O usuário já possui esse cargo");
        }

        // regra crítica: ADMIN não pode ser rebaixado
        if (currentRole == UserRole.ADMIN) {
            throw new BusinessException("Usuários ADMIN não podem ser rebaixados");
        }

        user.changeRole(newRole);

        userRepository.save(user);
    }

    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        if (user.getRole() == UserRole.ADMIN) {
            long activeAdmins = userRepository.countByRoleAndDeletedFalse(UserRole.ADMIN);

            if (activeAdmins <= 1) {
                throw new BusinessException("Não é possível remover o último administrador do sistema");
            }
        }

        user.softDelete();
        userRepository.save(user);
    }

    public void restoreUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new BusinessException("Usuário não encontrado"));

        if (!user.isDeleted()) {
            throw new BusinessException("Usuário já está ativo no sistema");
        }

        if (userRepository.existsByEmailIgnoreCaseAndDeletedFalse(user.getEmail())) {
            throw new BusinessException(
                    "Não é possível restaurar este usuário: email já está em uso"
            );
        }

        user.restore();

        userRepository.save(user);
    }
}



