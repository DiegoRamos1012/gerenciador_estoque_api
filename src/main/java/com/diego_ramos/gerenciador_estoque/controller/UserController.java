package com.diego_ramos.gerenciador_estoque.controller;

import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserUpdateDTO;
import com.diego_ramos.gerenciador_estoque.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void register(@RequestBody @Valid UserCreateDTO dto) {
        userService.register(dto);
    }

    // UPDATE NAME
    @PutMapping("/{id}/name")
    public void changeName(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeName(id, dto);
    }

    // UPDATE EMAIL
    @PutMapping("/{id}/email")
    public void changeEmail(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeEmail(id, dto);
    }

    // UPDATE PASSWORD
    @PutMapping("/{id}/password")
    public void changePassword(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changePassword(id, dto);
    }

    // UPDATE ROLE
    @PutMapping("/{id}/role")
    public void changeRole(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeRole(id, dto);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    // RESTORE
    @PutMapping("/{id}/restore")
    public void restoreUser(@PathVariable UUID id) {
        userService.restoreUser(id);
    }
}
