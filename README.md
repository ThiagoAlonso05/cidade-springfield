# 🏙️ Cidade Springfield – API de Serviços Municipais

Este projeto oferece uma API RESTful para o gerenciamento de cidadãos, usuários, pagamentos de IPTU e controle de solicitações públicas com histórico de estados. Tudo isso utilizando **Spring Boot**, **Spring State Machine**, **Spring Data JPA**, **OpenAPI Swagger**, **Open Feign** e **monitoramento com Prometheus**.

---

## 🚀 Funcionalidades

- CRUD de cidadãos
- Cadastro e autenticação de usuários
- Troca e bloqueio de senhas
- Pagamento de IPTU anual (parcelado ou à vista)
- Controle de parcelas pagas e a pagar
- Registro de solicitações com histórico via State Machine
- Métrica de usuários cadastrados com Prometheus
- Testes de endpoints via Feign Client
- Documentação via Swagger/OpenAPI

---

## ⚙️ Tecnologias utilizadas

- Java 21
- Spring Boot 3.4+
- Spring Web
- Spring Data JPA
- Spring State Machine
- Springdoc OpenAPI (Swagger)
- Feign Client
- Prometheus + Micrometer
- PostgreSQL (ou Azure SQL Server)
- Lombok