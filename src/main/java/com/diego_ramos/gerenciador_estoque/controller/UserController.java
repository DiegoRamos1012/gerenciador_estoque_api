package com.diego_ramos.gerenciador_estoque.controller;

import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserUpdateDTO;
import com.diego_ramos.gerenciador_estoque.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operações relacionadas a usuários")
@SecurityRequirement(name = "bearerAuth")
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
    @PatchMapping("/{id}/name")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Atualiza nome do usuário")
    public void changeName(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeName(id, dto);
    }

    // UPDATE EMAIL
    @PatchMapping("/{id}/email")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Atualiza e-mail do usuário")
    public void changeEmail(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeEmail(id, dto);
    }

    // UPDATE PASSWORD
    @PatchMapping("/{id}/password")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Atualiza senha do usuário")
    public void changePassword(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changePassword(id, dto);
    }

    // UPDATE ROLE
    @PatchMapping("/{id}/role")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') ")
    @Operation(summary = "Atualiza role do usuário")
    public void changeRole(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeRole(id, dto);
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
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
    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Restaura usuário removido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário restaurado")
    })
    public void restoreUser(@PathVariable UUID id) {
        userService.restoreUser(id);
    }
}
