FROM openjdk:17
ARG JAR_FILE=out/artifacts/OnlineMarket_jar/OnlineMarket.jar
WORKDIR /opt/app
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","app.jar"]