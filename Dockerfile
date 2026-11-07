FROM eclipse-temurin:17-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar

# Production: backends reach each other via the host IP 10.160.0.2:<port>
# (works with normal port publishing, e.g. -p <port>:<port>; no --network host needed).
ENV SPRING_PROFILES_ACTIVE=prod \
    SERVER_PORT=8087 \
    PORT=8087

EXPOSE 8087
ENTRYPOINT ["java","-jar","/app.jar"]
