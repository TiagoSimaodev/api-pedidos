🛒 API de Pedidos — Microserviço com Spring Boot, RabbitMQ, Docker e OpenAPI

Este projeto implementa uma API REST de Pedidos que envia mensagens para um exchange Fanout no RabbitMQ, 
permitindo integração com múltiplos microserviços (como Notificação, Processamento, etc.).
A arquitetura é baseada em mensageria e comunicação assíncrona, seguindo boas práticas modernas de microservices.

🚀 Tecnologias Utilizadas

Java 21

Spring Boot 3

Spring Web

Spring AMQP

RabbitMQ

Docker & Docker Compose

OpenAPI 3 / Swagger

Maven

🏗️ Arquitetura do Projeto

A API segue uma arquitetura orientada a eventos:

Client → API de Pedidos → RabbitMQ (Exchange: Fanout) → Múltiplas filas

Cada pedido recebido pela API é:

Validado

Persistido (opcional)

Serializado como JSON

Enviado para o exchange fanout

Consumido por outros microserviços

Como rodar o RabbitMQ com Docker: 

Execute: 

docker compose up -d

Após subir, acesse o Painel:

http://localhost:15672
user: rabbitmq
password: rabbitmq

🔧 Configuração AMQP

A API utiliza:

Exchange Fanout

Queue configurada via Application Properties

Jackson2JsonMessageConverter para serialização

RabbitAdmin para inicializar automaticamente as filas e exchange

📚 Documentação da API (Swagger) 

Após iniciar a aplicação, acesse: 

http://localhost:8080/swagger-ui/index.html

A documentação foi gerada usando SpringDoc OpenAPI, incluindo:

Descrição da API

Modelos das entidades

Exemplos de requisição

Códigos de resposta


📥 Endpoint Principal
POST /api/pedidos

Envia um pedido para o RabbitMQ.


📤 Fluxo de Envio

O controller recebe o pedido

O service valida e processa o objeto

O service envia para o exchange fanout via RabbitTemplate

Microserviços inscritos nas filas recebem a mensagem automaticamente


✔️ Objetivo do Projeto

Consolidar conhecimentos práticos de mensageria

Construir uma API moderna usando Spring Boot

Utilizar RabbitMQ e Docker em um fluxo real

Criar base para um sistema completo de microserviços


🧑‍💻 Autor

Francisco Tiago Rodrigues Simão
Desenvolvedor Java Full Stack | Spring Boot | Microservices


📧 E-mail:  tiagosimao.dev@gmail.com

🔗 LinkedIn: https://www.linkedin.com/in/tiagosimaodev/

🐙 GitHub: https://github.com/TiagoSimaodev






















