# 실행만 담당하는 이미지
FROM eclipse-temurin:17-jdk-jammy

# 로컬에서 빌드된 JAR 복사 (파일명 정확히 명시)
COPY build/libs/TalkTalkInterview-0.0.1-SNAPSHOT.jar app.jar

# 실행
ENTRYPOINT ["java", "-jar", "/app.jar"]
