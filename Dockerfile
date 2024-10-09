FROM maven:3.9.9-openjdk-17-slim AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17-jdk-alpine
WORKDIR /app
COPY --from=build /app/target/product-0.0.1-SNAPSHOT.jar app.jar

ARG JAVA_OPTS
ENV JAVA_OPTS=${JAVA_OPTS}
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]
