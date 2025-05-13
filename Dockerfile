# 1단계: Gradle로 build 수행
FROM gradle:7.6.0-jdk17 AS builder

WORKDIR /app
COPY . .

# Gradle 캐시 경로 명시 (권장), 테스트 제외하고 빌드
RUN gradle clean build -x test --no-daemon --gradle-user-home /app/.gradle

# 2단계: 실행용 경량 이미지
FROM eclipse-temurin:17-jdk-jammy

# 위 단계에서 만든 .jar 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]
