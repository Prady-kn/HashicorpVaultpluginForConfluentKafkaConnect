FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
WORKDIR /tmp/
RUN mvn package

FROM confluentinc/cp-kafka-connect
MAINTAINER Pradykn <emailtoprady@yahoo.com>
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/hashicorpvault-kafkaconnector-1.0-SNAPSHOT.jar /etc/kafka-connect/jars
# Alter below line appropriate to DB you want to use.
COPY sqljdbc/mssql-jdbc-7.2.2.jre8.jar /etc/kafka-connect/jars
ENV CONNECT_PLUGIN_PATH=/usr/share/java,/etc/kafka-connect/jars
ENV CONNECT_CONFIG_PROVIDERS=vault
