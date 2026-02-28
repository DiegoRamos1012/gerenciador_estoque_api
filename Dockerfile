# ===============================
# Etapa 1 - Build da aplicação
# ===============================
FROM maven:3.9.12-eclipse-temurin-25 AS build

WORKDIR /app

COPY pom.xml .
COPY mvnw mvnw.cmd ./
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests -B

# ===============================
# Etapa 2 - Imagem final leve
# ===============================
FROM eclipse-temurin:25-jre

WORKDIR /app

# ===============================
# Cria pasta para o banco SQLite
# ===============================
RUN mkdir -p /app/data

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]