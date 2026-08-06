# Stage 1: Build application
FROM maven:3.9.8-amazoncorretto-21 AS build
WORKDIR /app

# Copy pom.xml và tải trước dependencies để tận dụng Docker layer cache
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Copy source code và tiến hành đóng gói JAR
COPY src ./src
RUN mvn package -DskipTests

# Stage 2: Runtime image
FROM amazoncorretto:21.0.4-alpine
WORKDIR /app

# Copy file jar đã build từ Stage 1
COPY --from=build /app/target/*.jar app.jar

# Khai báo port Spring Boot
EXPOSE 8080

# Chạy ứng dụng
ENTRYPOINT ["java", "-jar", "app.jar"]
