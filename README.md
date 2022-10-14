# kafka-examples

Docker composes:
- default: 1 cmak(kafka manager) e 1 cluster com 2 kafka brokers e 1 zookeeper
- 1: 1 cmak e 1 cluster com 2 kafka brokers, 1 zookeeper - com erro de configuracao no kafka broker 2
- 2: 1 cmak e 1 cluster com 1 kafka broker, 1 zookeeper
- 3: 1 cmak e 2 clusters cada 1 com com 1 kafka broker e 1 zookeeper

Cenarios (foram divididos em profiles)
- 1: falha no producer, callback de error
  - utilizar docker-compose-1.yml
  - criar topic-1 com 2 particoes e fator de replicacao 2
    - ha um problema na config do broker 2 que impede o envio da mensagem

- 2: lag
  - utilizar docker-compose-2.yml
  - criar topic-1 com 10 particoes e fator de replicacao 1
  - utilizar script k6 para produzir mensagens constantes (2 rps) - consumer esta com sleep de 1s
    - 2.1: solucao aumentando o numero de concorrencia usando novas instancias de consumidores 
    - 2.2: solucao aumentando o numero de concorrencia usando threads
  - observacoes:
    - nao adianta ter a configuracao de paralelismo feita se:
      - nao houver particoes disponiveis para o paralelismo
      - se a mensagem for enviada somente para a mesma particao (atencao a key ou a partition no producer)
    - nao adianta ter mais threads(concurrency) do que particoes - vao ficar ociosas

- [Opcional] 3: configurando multiplos clusters
  - utilizar docker-compose-3.yml
  - configurar 2 clusters no kafka manager (cmak)
  - criar topic-1-1 no cluster1 com 10 particoes e fator de replicacao 1
  - criar topic-2-1 no cluster2 com 10 particoes e fator de replicacao 1
    - 3.1: produtor e consumidor em clusters diferente
    - 3.2: produtores de clusters diferentes

- [Opcional] 4: utilizando eventos/mensagens como json
  - utilizar docker-compose-2.yml
  - criar topic-1 com 10 particoes e fator de replicacao 1
  - postar mensagem no topic-1 no formato de json
  - postar mensagem com erro de formatacao no topic-1 no formato de json

Como alterar sobrescrever a configuracao do application via linha de comando:
mvn spring-boot:run -Dspring-boot.run.profiles=1
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8082
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8083

Complementos:
- @SendTo
- @RetryableTopic

Referencias:
https://docs.spring.io/spring-kafka/reference/html/#container-props