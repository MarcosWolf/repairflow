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

- <b>Java:</b> linguagem de programação principal
- <b>Maven:</b> gerenciamento de dependências e build

### Backend

- <b>Spring Boot:</b> framework principal para a API REST
- <b>H2 Database:</b> usado em memória apenas para testes; desenvolvimento usa banco persistido
- <b>JPA/Hibernate:</b> persistência de dados com ORM

### Frontend

- <b>JavaFX:</b> interface gráfica desktop moderna e responsiva

### Testes

- <b>JUnit:</b> testes unitários e de integração
- <b>Mockito:</b> framework de mocking para testes isolados
- <b>REST Assured:</b> testes automatizados da API REST
- <b>Validações:</b> validação de formulários e regras de negócio
- <b>TestFX:</b> framework para testes automatizados de interface gráfica JavaFX

## Executando a Alicação

1. Clone o repositório:

```bash
git clone https://github.com/seu-usuario/crm-reparos.git
cd crm-reparos
```

2. Compile o projeto:
```bash
mvn clean install
```
   
3. Execute a aplicação:
```bash
mvn spring-boot:run
```

A aplicação iniciará tanto o servidor Spring Boot quanto a interface JavaFX automaticamente.


## Executando os Testes

Para rodar todos os testes:

```bash
mvn test
```

Para testes específicos:

```bash
mvn test -Dtest=NomeDaClasse
```

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
│   │   │       ├── loader/           # Componentes de inicialização
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
│               └── ui/              
│                   ├── controller/  # Testes de interface gráfica TestFX
│                   └── handler/     # Testes de Validators
├── pom.xml
└── README.md
```

## API REST

A aplicação expõe uma API REST completa para integração com outros sistemas:

- `GET/POST/DELETE /api/v1/cliente` – Gerenciamento de clientes
- `GET/POST/DELETE /api/v1/tipo-equipamento` – Controle de Tipos de equipamentos
- `GET/POST/DELETE /api/v1/equipamento` – Controle de equipamentos
- `GET/POST/DELETE /api/v1/status-reparo` – Controle de Status de reparos
- `GET/POST/DELETE /api/v1/reparo` – Ordens de serviço

> **Nota:** O endpoint **POST** realiza tanto criação quanto atualização de registros (upsert pattern).

## Banco de Dados

O projeto utiliza H2 Database em modo embedded. Para acessar o console H2:

1. Acesse `http://localhost:8080/h2-console`
2. JDBC URL: `jdbc:h2:file:./data/crm_reparos`
3. User: `sa`
4. Password: (deixe em branco)
