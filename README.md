# kafka-examples

Cenarios (foram divididos em profiles)
- 1: falha no producer, callback de error
  - utilizar docker-compose-1.yml
  - criar topic-1 com 2 particoes e fator de replicacao 2
    - ha um problema na config do broker 2 que impede o envio da mensagem
- 2: lag
  - utilizar docker-compose.yml
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
    - 3.1: produtor e consumidor em clusters diferente
    - 3.2: produtores de clusters diferentes

Como alterar sobrescrever a configuracao do application via linha de comando:
mvn spring-boot:run -Dspring-boot.run.profiles=1
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8082
mvn spring-boot:run -Dspring-boot.run.arguments=--server.port=8083