# 1. Aşama: Maven ile proje derlenir
FROM maven:3.9.5-eclipse-temurin-17 AS builder
WORKDIR /app

# Önce sadece pom.xml kopyalanır ve bağımlılıklar çekilir
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Şimdi tüm proje dosyaları kopyalanır
COPY . .
RUN mvn clean package -DskipTests

# 2. Aşama: JAR dosyasını çalıştırmak için ortam hazırlanır
FROM openjdk:17-jdk-slim
VOLUME /tmp
WORKDIR /app

# JAR dosyasını build aşamasından kopyala
COPY --from=builder /app/target/*.jar /app/

# JAR adını dinamik olarak tespit et ve başlat
RUN ls /app/*.jar > jar-name.txt \
 && sed -i 's#.*/##' jar-name.txt

CMD ["sh", "-c", "java -jar $(cat jar-name.txt)"]