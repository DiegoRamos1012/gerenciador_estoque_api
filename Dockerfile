# ===============================
# Etapa 1 - Build da aplicação
# ===============================
FROM maven:3.9.12-eclipse-temurin-25 AS build

WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline

COPY src ./src
RUN mvn clean package -Dmaven.test.skip=true


# ===============================
# Etapa 2 - Imagem final leve
# ===============================
FROM eclipse-temurin:25-jdk-alpine

WORKDIR /app

# 🔥 cria pasta para o SQLite
RUN mkdir -p /app/data

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]