package com.diego_ramos.gerenciador_estoque.controller;

import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserCreateDTO;
import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserUpdateDTO;
import com.diego_ramos.gerenciador_estoque.dto.userDTO.UserUpdateRoleDTO;
import com.diego_ramos.gerenciador_estoque.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@Tag(name = "Users", description = "Operações relacionadas a usuários")
@SecurityRequirement(name = "basicAuth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // CREATE
    @PostMapping
    @Operation(summary = "Registra um novo usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida ou e-mail já cadastrado")
    })
    public ResponseEntity<Map<String, String>> register(@RequestBody @Valid UserCreateDTO dto) {
        userService.register(dto);
        String msg = "Usuário criado com sucesso!";
        return ResponseEntity.status(HttpStatus.CREATED)
                .header("X-Success-Message", msg)
                .body(Map.of("message", msg));
    }

    // UPDATE NAME
    @PatchMapping("/{id}/name")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Atualiza nome do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Nome atualizado com sucesso"),
            @ApiResponse(responseCode = "403", description = "Acesso negado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado")
    })
    public ResponseEntity<Map<String, String>> changeName(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeName(id, dto);
        String msg = "Nome atualizado com sucesso!";
        return ResponseEntity.ok()
                .header("X-Success-Message", msg)
                .body(Map.of("message", msg));
    }

    // UPDATE EMAIL
    @PatchMapping("/{id}/email")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Atualiza e-mail do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "E-mail atualizado com sucesso")
    })
    public ResponseEntity<Map<String, String>> changeEmail(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changeEmail(id, dto);
        String msg = "E-mail atualizado com sucesso!";
        return ResponseEntity.ok()
                .header("X-Success-Message", msg)
                .body(Map.of("message", msg));
    }

    // UPDATE PASSWORD
    @PatchMapping("/{id}/password")
    @PreAuthorize("hasRole('ADMIN') or #id == authentication.principal.id")
    @Operation(summary = "Atualiza senha do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Senha alterada com sucesso")
    })
    public ResponseEntity<Map<String, String>> changePassword(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateDTO dto) {

        userService.changePassword(id, dto);
        String msg = "Senha alterada com sucesso!";
        return ResponseEntity.ok()
                .header("X-Success-Message", msg)
                .body(Map.of("message", msg));
    }

    // UPDATE ROLE
    @PatchMapping("/{id}/role")
    @PreAuthorize("hasAnyRole('MANAGER', 'ADMIN') ")
    @Operation(summary = "Atualiza role do usuário")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cargo atualizado com sucesso")
    })
    public ResponseEntity<Map<String, String>> changeRole(
            @PathVariable UUID id,
            @RequestBody @Valid UserUpdateRoleDTO dto) {

        userService.changeRole(id, dto);
        String msg = "Cargo atualizado com sucesso!";
        return ResponseEntity.ok()
                .header("X-Success-Message", msg)
                .body(Map.of("message", msg));
    }

    // SOFT DELETE
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Remove usuário (soft delete)")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Usuário removido com sucesso (No Content)")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent()
                .header("X-Success-Message", "Usuário removido")
                .build();
    }

    // RESTORE
    @PatchMapping("/{id}/restore")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Restaura usuário removido")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Usuário restaurado com sucesso")
    })
    public ResponseEntity<Map<String, String>> restoreUser(@PathVariable UUID id) {
        userService.restoreUser(id);
        String msg = "Usuário restaurado com sucesso!";
        return ResponseEntity.ok()
                .header("X-Success-Message", msg)
                .body(Map.of("message", msg));
    }
}