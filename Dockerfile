# Etapa de build: usa Maven com JDK 17
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copia todo o projeto e realiza o build (sem testes)
COPY . .
RUN mvn clean package -DskipTests

# Etapa de runtime: apenas a JRE (mais leve)
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia o JAR gerado da etapa de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta padrão do Spring Boot
EXPOSE 8080

# Comando para iniciar a aplicação
ENTRYPOINT ["java", "-jar", "app.jar"]
