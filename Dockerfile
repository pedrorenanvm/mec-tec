# --- Estágio 1: Build com Maven ---
# Usamos uma imagem oficial do Maven com JDK 17 para compilar o projeto
FROM maven:3.9-eclipse-temurin-17 AS builder

# Define o diretório de trabalho dentro do container
WORKDIR /app

# Copia apenas o pom.xml para aproveitar o cache do Docker
# As dependências só serão baixadas novamente se o pom.xml mudar
COPY pom.xml .

# Baixa todas as dependências do projeto
RUN mvn dependency:go-offline

# Copia o resto do código-fonte
COPY src ./src

# Compila o projeto e gera o arquivo .jar, pulando os testes
RUN mvn package -DskipTests

# --- Estágio 2: Execução ---
# Usamos uma imagem JRE (Java Runtime Environment) que é bem menor
FROM eclipse-temurin:17-jre-jammy

# Define o diretório de trabalho
WORKDIR /app

# Expõe a porta que a aplicação Spring Boot usa por padrão
EXPOSE 8080

# Copia o arquivo .jar gerado no estágio de build para a imagem final
COPY --from=builder /app/target/mec-tec-0.0.1-SNAPSHOT.jar app.jar

# Comando para iniciar a aplicação quando o container rodar
ENTRYPOINT ["java", "-jar", "app.jar"]