FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE=/build/libs/mrex-prcn-price-cachemanager-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} mrex-prcn-price-cachemanager-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/mrex-prcn-price-cachemanager-0.0.1-SNAPSHOT.jar"]