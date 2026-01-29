package com.diego_ramos.gerenciador_estoque.controller;

import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserUpdateDTO;
import com.diego_ramos.gerenciador_estoque.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operações relacionadas a usuários")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Registra um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    public void register(@RequestBody @Valid UserCreateDTO dto) {
        userService.register(dto);
    }

    // UPDATE NAME
    @PutMapping("/{id}/name")
    @Operation(summary = "Atualiza nome do usuário")
    public void changeName(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeName(id, dto);
    }

    // UPDATE EMAIL
    @PutMapping("/{id}/email")
    @Operation(summary = "Atualiza e-mail do usuário")
    public void changeEmail(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeEmail(id, dto);
    }

    // UPDATE PASSWORD
    @PutMapping("/{id}/password")
    @Operation(summary = "Atualiza senha do usuário")
    public void changePassword(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changePassword(id, dto);
    }

    // UPDATE ROLE
    @PutMapping("/{id}/role")
    @Operation(summary = "Atualiza role do usuário")
    public void changeRole(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeRole(id, dto);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Remove usuário (soft delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public void deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
    }

    // RESTORE
    @PutMapping("/{id}/restore")
    @Operation(summary = "Restaura usuário removido")
    public void restoreUser(@PathVariable UUID id) {
        userService.restoreUser(id);
    }
}
