FROM maven:3.6.3-jdk-8 AS jvm_demo_build

RUN mkdir /app
WORKDIR /app
COPY ./src /app/src
COPY pom.xml /app
RUN mvn clean package

FROM openjdk:8-alpine

RUN mkdir /app
WORKDIR /app

COPY --from=jvm_demo_build /app/target/jvm-chaos-demo-*.jar /app/jvm-chaos-demo.jar

ENTRYPOINT [ "sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar jvm-chaos-demo.jar" ]