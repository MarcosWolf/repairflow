# crm-reparos

Sistema completo de gestão para oficinas de reparos eletrônicos, desenvolvido com Spring Boot e JavaFX. Gerencie clientes, equipamentos, ordens de serviço e pagamentos através de uma interface desktop moderna e intuitiva.

## Sobre

O CRM Reparos é uma solução desktop robusta para oficinas e técnicos de eletrônica que precisam organizar e acompanhar reparos de forma profissional. A aplicação integra uma API REST construída com Spring Boot a uma interface gráfica JavaFX, oferecendo uma experiência completa e centralizada.

### Principais Funcionalidades

- <b>Gestão de Clientes:</b> cadastre e mantenha informações detalhadas de seus clientes
- <b>Controle de Equipamentos:</b> registre equipamentos com histórico completo de reparos
- <b>Ordens de Serviço:</b> crie, acompanhe e finalize ordens de reparo com facilidade
- <b>Controle Financeiro:</b> gerencie pagamentos e acompanhe o status de cada serviço
- <b>Interface Intuitiva:</b> navegação simples e design limpo para agilizar o dia a dia

## Tecnologias Utilizadas

### Core

- <b>Java:<b> linguagem de programação principal
- <b>Maven:</b> gerenciamento de dependências e build

### Backend

- <b>Spring Boot:</b> framework principal para a API REST
- <b>H2 Database:</b> banco de dados em memória para desenvolvimento e testes
- <b>JPA/Hibernate:</b> persistência de dados com ORM

### Frontend

- <b>JavaFX:</b> interface gráfica desktop moderna e responsiva

### Testes

- <b>JUnit:</b> testes unitários
- <b>Mockito:</b> framework de mocking para testes isolados
- <b>REST Assured:</b> testes automatizados da API REST

## Estrutura do Projeto

```text
crm-reparos/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/marcoswolf/crm/reparos/
│   │   │       ├── business/        # Lógica de negócio
│   │   │       ├── controller/      # Controladores REST
│   │   │       ├── infrastructure/  # Entities e repositories
│   │   │       │   ├── entities/    # Classes de domínio (Cliente, Reparo, Equipamento, etc.)
│   │   │       │   └── repositories/ # Interfaces de acesso a dados
│   │   │       └── ui/              # Interface JavaFX
│   │   │           ├── controller/  # Controladores das views
│   │   │           ├── handler/     # Validações e actions
│   │   │           ├── shared/      # Classes compartilhadas entre controllers
│   │   │           ├── mappers/     # Form mappers
│   │   │           ├── navigation/  # Navegação entre views
│   │   │           ├── tables/      # TableView helpers
│   │   │           └── utils/       # Utilitários gerais
│   │   └── resources/
│   │       ├── application.properties
│   │       ├── css/
│   │       └── fxml/                 # Layouts JavaFX
│   └── test/
│       └── java/
│           └── com/marcoswolf/crm/reparos/
│               ├── business/        # Testes unitários e de integração do business
│               ├── controller/      # Testes REST com RestAssured
│               └── ui/              # Testes de validators e handlers
├── pom.xml
└── README.md
```
