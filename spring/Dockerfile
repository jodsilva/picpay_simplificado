FROM maven:3.9.11-eclipse-temurin-21

WORKDIR /app

COPY . .

RUN mvn dependency:go-offline -B

# Expondo a porta do Spring
EXPOSE 8080

# Rodando via Maven para DevTools funcionar
CMD ["mvn", "spring-boot:run"]