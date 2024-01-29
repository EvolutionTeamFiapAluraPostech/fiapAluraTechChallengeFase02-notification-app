# Pós-Tech-FIAP/ALURA-Fase02

![Badge em Desenvolvimento](http://img.shields.io/static/v1?label=STATUS&message=EM%20DESENVOLVIMENTO&color=GREEN&style=for-the-badge)

# Descrição do projeto
Repositório do projeto da pós tech da FIAP/ALURA. Desenvolvimento de uma solução de NOTIFICACÃ́O do usuário do aplicativo de parquímetro, utilizando conceitos de APIs e persistência de dados.

## Requisitos:
1. Utilize APIs modernas para melhorar a eficiência do sistema. Por exemplo, utilize as classes e métodos da API de datas e horas do Java para facilitar o cálculo do tempo de estacionamento.
2. Implemente uma estrutura de persistência de dados eficiente. Utilize um banco de dados (pode ser em memória, físico, SQL ou NoSQL), para armazenar as informações sobre os veículos estacionados. Isso permitirá um acesso rápido e confiável aos dados.
3. Otimize os processos de gravação e leitura dos dados. Utilize técnicas para minimizar a necessidade de acesso frequente ao banco de dados. Isso ajudará a reduzir atrasos e melhorar o desempenho geral do sistema.
4. Considere a escalabilidade do sistema. Embora o desafio não exija a implementação de um sistema distribuído, é importante projetar a solução de forma que ela possa lidar com um grande volume de dados e ser facilmente escalável no futuro.

## Entregáveis:
1. Link do Github com o código fonte dos serviços desenvolvidos.
2. Documentação técnica (pode ser em JavaDoc, Swagger, etc).
3. Um relatório técnico descrevendo as tecnologias e ferramentas utilizadas, os desafios encontrados durante o desenvolvimento e as soluções implementadas para resolvê-las.

# Tecnologias utilizadas
1. Java 17
2. Spring Boot 3.1.2
3. Spring Security
4. Spring Web MVC
5. Spring Data JPA
6. Spring Bean Validation
7. Spring Open Feign
8. Spring Doc Open API
9. Lombok
10. Postgres 15.1
11. Flyway
12. JUnit
13. Mockito
14. TestContainers
15. Wiremock
16. Docker
17. Ethereal - fake SMTP

# Setup do Projeto

Para realizar o setup do projeto é necessário possuir o Java 17, docker 24 e docker-compose 1.29 instalado em sua máquina.
Faca o download do projeto (https://github.com/EvolutionTeamFiapAluraPostech/fiapAluraTechChallengeFase02-notification-app) e atualize suas dependências com o gradle.
Antes de iniciar o projeto é necessário criar o banco de dados. O banco de dados está programado para ser criado em um container. 
Para criar o container, execute o docker-compose.
Acesse a pasta raiz do projeto, no mesmo local onde encontra-se o arquivo docker-compose.yml. Para executá-lo, execute o comando docker-compose up -d (para rodar detached e não prender o terminal).
Para iniciar o projeto, basta executar o Spring Boot Run no IntelliJ.
Após a inicialização do projeto, as task schedulers serão executadas de 1/1 minuto.
A aplicação estará pronta para receber requisições de notificações de pagamento e encerramento de estacionamento.

# Documentação da API
http://localhost:8081/swagger-ui/index.html

# Documentação do PROJETO
https://www.notion.so/Evolution-Team-Digital-Parking-96c5767bc5ac4506b4497998f261fef9?pvs=4

# Collection do Postman
* Marcelo-RM350802-Fiap-Alura-Arq-Dev-Java-Tech-Challenge-Fase-02.postman_collection.json
* Esta collection está salva na raiz do projeto.
