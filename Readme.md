# Docker image for Confluent Kafka Connect with Hashicorp Vault Plugin
***

Configure the Hashicrop Vault with required secrets and start the container as shown below.

### Running the container:
*** 

`docker run -d --name=cp-kafkaconnect-with-hashicorpvault --hostname kafkaconnect --link zookeeper --link kafka -p 28083:28083 -e CONNECT_BOOTSTRAP_SERVERS=kafka:29092 -e CONNECT_REST_PORT=28083 -e CONNECT_GROUP_ID="quickstart-avro" -e CONNECT_CONFIG_STORAGE_TOPIC="quickstart-avro-config" -e CONNECT_OFFSET_STORAGE_TOPIC="quickstart-avro-offsets" -e CONNECT_STATUS_STORAGE_TOPIC="quickstart-avro-status" -e CONNECT_CONFIG_STORAGE_REPLICATION_FACTOR=1 -e CONNECT_OFFSET_STORAGE_REPLICATION_FACTOR=1 -e CONNECT_STATUS_STORAGE_REPLICATION_FACTOR=1 -e CONNECT_KEY_CONVERTER="org.apache.kafka.connect.json.JsonConverter" -e CONNECT_VALUE_CONVERTER="org.apache.kafka.connect.json.JsonConverter" -e CONNECT_INTERNAL_KEY_CONVERTER="org.apache.kafka.connect.json.JsonConverter" -e CONNECT_INTERNAL_VALUE_CONVERTER="org.apache.kafka.connect.json.JsonConverter" -e CONNECT_REST_ADVERTISED_HOST_NAME="localhost" -e CONNECT_LOG4J_ROOT_LOGLEVEL=INFO -e CONNECT_CONFIG_PROVIDERS=vault -e CONNECT_CONFIG_PROVIDERS_VAULT_CLASS=com.pradykn.kafkaconnect.core.VaultConfigProvider -e CONNECT_CONFIG_PROVIDERS_VAULT.PARAM_ROLEID=c33f5084-ec88-433d-a238-d19b8cf498c9 -e CONNECT_CONFIG_PROVIDERS_VAULT.PARAM_SECID=4798b72b-b3a3-f241-4b02-cc45dfff4240 -e CONNECT_CONFIG_PROVIDERS_VAULT.PARAM_VAULTURL="http://172.17.0.2:8200/v1" pradykn/confluent-kafka-connect-with-hashicorpvaultplugin`



### Note:
>This is **not an official** plugin.