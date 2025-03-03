# OpenJDK 기반의 공식 이미지 사용
FROM eclipse-temurin:17-jdk

# 작업 디렉토리 설정
WORKDIR /app

# JAR 파일 복사
COPY *.jar tech-ensemble-log-db-0.0.1.jar

# 7001 포트 오픈
EXPOSE 7001

# 컨테이너가 실행될 때 실행할 명령어
CMD ["java", "-jar", "tech-ensemble-log-db-0.0.1.jar"]