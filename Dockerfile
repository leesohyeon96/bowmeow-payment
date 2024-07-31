# Java로 작성된 어플리케이션은 OpenJDK 이미지 필요
FROM openjdk:21-jdk-slim

LABEL authors="shl"

# working directory 설정
WORKDIR /app

# 어플리케이션 Jar file 를 /app 작업 디렉토리에 Copy
COPY build/libs/payment-0.0.1-SNAPSHOT.jar /app/payment-0.0.1-SNAPSHOT.jar

# Expose port 설정
EXPOSE 8080

# 어플리케이션 명령어 정의
ENTRYPOINT ["java", "-jar", "payment-0.0.1-SNAPSHOT.jar"]