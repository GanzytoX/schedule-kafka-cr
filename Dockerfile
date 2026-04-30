FROM maven:3.9-eclipse-temurin-21 AS build
WORKDIR /app

# Copiar el pom y descargar dependencias
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copiar el código fuente
COPY src ./src
RUN mvn clean package -DskipTests

# Imagen final
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Puerto por defecto del broker
EXPOSE 3000

ENTRYPOINT ["java", "-jar", "app.jar"]
