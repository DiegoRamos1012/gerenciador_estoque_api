package com.diego_ramos.gerenciador_estoque;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

/**
 * Testes de inicialização do contexto do Spring Boot para a aplicação GerenciadorEstoque.
 *
 * <p>
 * Esta classe verifica se o contexto da aplicação consegue ser carregado corretamente
 * sem lançar exceções. Ela não testa lógica de negócio específica, apenas garante
 * que a configuração do Spring, dos beans e dos perfis ativos está correta.
 * </p>
 *
 * <p>
 * Anotações utilizadas:
 * <ul>
 *     <li>{@link SpringBootTest}: Inicia o contexto completo do Spring Boot para os testes.
 *     Isso inclui carregamento de beans, configuração JPA, DataSource, etc.</li>
 *
 *     <li>{@link ActiveProfiles("test")}: Define que o perfil ativo para este teste é "test".
 *     Normalmente, o perfil "test" utiliza banco de dados H2 em memória ou configurações
 *     específicas para testes, evitando interferir no banco de produção.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Método de teste:
 * <ul>
 *     <li>{@link #contextLoads()}: Testa se o contexto do Spring Boot carrega sem erros.
 *     Este é um teste padrão gerado pelo Spring Initializr, útil para garantir que
 *     a aplicação inicia corretamente.</li>
 * </ul>
 * </p>
 *
 * <p>
 * Exemplo de execução:
 * <pre>
 * mvn test
 * </pre>
 * </p>
 *
 * @author Diego Ramos
 * @since 0.0.1
 */
@SpringBootTest
@ActiveProfiles("test")
class GerenciadorEstoqueApplicationTests {

    /**
     * Teste que verifica se o contexto da aplicação consegue ser inicializado
     * sem lançar exceções.
     */
    @Test
    void contextLoads() {
        // Este teste passa se o Spring Boot carregar o contexto com sucesso
    }

}
