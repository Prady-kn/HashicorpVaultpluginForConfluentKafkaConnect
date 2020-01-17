FROM confluentinc/cp-kafka-connect
MAINTAINER Pradykn <emailtoprady@yahoo.com>
COPY target/hashicorpvault-kafkaconnector-1.0-SNAPSHOT.jar /etc/kafka-connect/jars
COPY sqljdbc/mssql-jdbc-7.2.2.jre8.jar /etc/kafka-connect/jars
ENV CONNECT_PLUGIN_PATH=/usr/share/java,/etc/kafka-connect/jars