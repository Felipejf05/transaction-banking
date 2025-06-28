# Etapa de build (opcional, se quiser gerar o jar dentro do container)
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Etapa de runtime
FROM eclipse-temurin:17-jre
WORKDIR /app

# Copia o jar gerado da etapa anterior
COPY --from=build /app/target/*.jar app.jar

#Copia o script wait-for-it-sh.
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh

# Expõe a porta que sua API usa (geralmente 8080)
EXPOSE 8080

#Aguarda o MySQL estar acessível antes de rodar o JAR
ENTRYPOINT ["/wait-for-it.sh", "mysql:3306", "--", "java", "-jar", "app.jar"]
