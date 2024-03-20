FROM openjdk:17-jdk-slim

WORKDIR /app

COPY pom.xml .
RUN mvn clean install

COPY target/my-service-*.jar .

CMD ["java", "-jar", "my-service-*.jar"]
