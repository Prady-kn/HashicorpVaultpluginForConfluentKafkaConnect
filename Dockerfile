FROM maven:3.5.2-jdk-8-alpine AS MAVEN_TOOL_CHAIN
COPY pom.xml /tmp/
COPY src /tmp/src/
# Use jdbc driver accordingly, here is using mssql-jdbc-7.2.2.jre8.jar.
COPY sqljdbc/mssql-jdbc-7.2.2.jre8.jar /tmp/
WORKDIR /tmp/
RUN mvn package

FROM confluentinc/cp-kafka-connect
ENV CONNECT_PLUGIN_PATH=/usr/share/java,/etc/kafka-connect/jars CONNECT_CONFIG_PROVIDERS=vault CONNECT_CONFIG_PROVIDERS_VAULT_CLASS=com.pradykn.kafkaconnect.core.VaultConfigProvider

# Use jdbc driver accordingly, here is using mssql-jdbc-7.2.2.jre8.jar.
COPY --from=MAVEN_TOOL_CHAIN /tmp/target/hashicorpvault-kafkaconnector-1.0-SNAPSHOT.jar /tmp/mssql-jdbc-7.2.2.jre8.jar /etc/kafka-connect/jars/


