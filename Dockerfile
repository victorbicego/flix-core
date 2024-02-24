FROM maven:latest as build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:21-oracle
WORKDIR /opt/app
COPY --from=build /app/target/core-*.jar ./core.jar
CMD ["java", "-jar", "core.jar"]