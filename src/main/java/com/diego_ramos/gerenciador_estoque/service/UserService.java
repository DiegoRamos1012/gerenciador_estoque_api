package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.domain.User;
import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserUpdateDTO;
import com.diego_ramos.gerenciador_estoque.exceptions.BusinessException;
import com.diego_ramos.gerenciador_estoque.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void userCreate(UserCreateDTO dto) {
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

    public void changeName(@NonNull UserUpdateDTO dto, UUID id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new BusinessException(("Usuário não encontrado")));

        if (userRepository.existsByNameIgnoreCase(dto.name()) &&
                !user.getName().equalsIgnoreCase(dto.name())) {
            throw new BusinessException("Já existe um usuário com esse nome");
        }

        user.changeName(dto.name());
        userRepository.save(user);
    }

    public void changeEmail(@NonNull UserUpdateDTO dto, UUID id) {
        User user = userRepository.findById(id).
                orElseThrow(() -> new BusinessException(("Usuário não encontrado")));
    }

    
}
