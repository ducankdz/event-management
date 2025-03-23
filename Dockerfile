# Stage 1: Build ứng dụng
FROM maven:3.9.5-amazoncorretto-17 AS build

WORKDIR /app

# ARG để chỉ định repo local
ARG MAVEN_REPO=/root/.m2/repository

# Copy pom.xml trước để cache dependencies
COPY pom.xml ./pom.xml

# Tải dependencies và lưu vào MAVEN_REPO
RUN mvn dependency:go-offline -Dmaven.repo.local=$MAVEN_REPO -B

# Copy toàn bộ mã nguồn
COPY src ./src

# Build ứng dụng
RUN mvn clean package -DskipTests -Dmaven.repo.local=$MAVEN_REPO

# Stage 2: Chạy ứng dụng
FROM openjdk:17-alpine

WORKDIR /usr/local/lib

COPY --from=build /app/target/event-manager.jar event-manager.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "event-manager.jar"]
