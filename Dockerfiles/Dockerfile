# Aşama 1: Build aşaması
FROM maven:3.8-openjdk-17-slim AS build

# Uygulama kodunu ekle
WORKDIR /app
COPY ./pom.xml .
COPY ./src ./src

# Maven ile uygulamayı derleyip jar üret
RUN mvn clean package -DskipTests

# Aşama 2: Çalıştırma aşaması
FROM openjdk:17-jdk-slim

# Uygulama dosyasını kopyala
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Uygulamanın çalıştırılması
ENTRYPOINT ["java", "-jar", "app.jar"]
