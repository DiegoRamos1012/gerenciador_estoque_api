package com.diego_ramos.gerenciador_estoque.service;

import com.diego_ramos.gerenciador_estoque.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public @NonNull UserDetails loadUserByUsername(@NonNull String email)
            throws UsernameNotFoundException {

        return userRepository.findByEmailIgnoreCaseAndDeletedFalse(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("Usuário não encontrado"));
    }
}
