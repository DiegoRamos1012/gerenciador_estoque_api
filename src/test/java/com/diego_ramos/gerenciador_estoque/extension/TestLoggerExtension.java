package com.diego_ramos.gerenciador_estoque.extension;

import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TestLoggerExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {

    private static final Logger logger = LoggerFactory.getLogger(TestLoggerExtension.class);

    @Override
    public void beforeTestExecution(ExtensionContext context) {
        String testName = context.getDisplayName();
        logger.info("Início do teste: {}", testName);
    }

    @Override
    public void afterTestExecution(ExtensionContext context) {
        String testName = context.getDisplayName();
        logger.info("Fim do teste: {}", testName);
    }
}
