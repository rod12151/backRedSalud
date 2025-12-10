# ========================
# Etapa 1: Build con Maven
# ========================
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# Copiar pom.xml e instalar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline

# Copiar el resto del código y compilar
COPY . .
RUN mvn clean package -DskipTests

# ========================
# Etapa 2: Imagen final (ejecución)
# ========================
FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

# Copiar el jar compilado
COPY --from=build /app/target/*.jar app.jar

# Exponer el puerto (ajústalo si tu Spring usa otro)
EXPOSE 8080

# Arrancar Spring Boot
ENTRYPOINT ["java", "-jar", "app.jar"]

#docker compose up --build