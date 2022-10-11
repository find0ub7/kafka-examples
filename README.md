# kafka-examples

Cenarios
- produtor x consumidor simples:
  - 1 produtor configurado usando a convencao
  - 1 consumidor configurado usando a convencao
  - 1 zookeeper, 1 kafka broker
  - 1 topico com 1 particao


mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8082
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8083