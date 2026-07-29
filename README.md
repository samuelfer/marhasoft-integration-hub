# MarhaSoft Integration Hub

O **MarhaSoft Integration Hub** é um framework Java desenvolvido para simplificar a integração entre sistemas corporativos e plataformas externas.

A arquitetura é baseada em um **Pipeline de execução**, onde cada integração é composta por etapas independentes (*Steps*), permitindo validação, resolução de dependências, transformação de dados, autenticação, transporte e auditoria de forma desacoplada.

## Objetivo

Construir uma plataforma reutilizável para integração entre sistemas, reduzindo o acoplamento entre ERPs e serviços externos através de uma arquitetura baseada em Connectors, Pipeline e Resolvedores de Dependências.

---

# Principais características

- Arquitetura baseada em Pipeline
- Connectors para diferentes provedores
- Resolução automática de dependências
- Validação desacoplada
- Transformação de dados (Mapping)
- Integração com autenticação OAuth2/Cerberus
- Auditoria das integrações
- Arquitetura modular e extensível
- Preparado para múltiplos provedores (TCE, eSocial, Receita Federal, Banco Central, APIs corporativas e outros)

---

# Arquitetura

```text
ERP / Sistema Integrador
            │
            ▼
     Integration Pipeline
            │
            ▼
       Validation Step
            │
            ▼
 Dependency Resolution Step
            │
            ▼
       Mapping Step
            │
            ▼
    Authentication Step
            │
            ▼
      Transport Step
            │
            ▼
        Audit Step
            │
            ▼
          Response
```

---

# Fluxo de uma integração

Cada integração percorre sempre o mesmo fluxo:

1. Validação da requisição.
2. Resolução das dependências necessárias.
3. Transformação para o payload do sistema de destino.
4. Autenticação.
5. Envio da requisição.
6. Registro da auditoria.
7. Retorno do resultado.

---

# Responsabilidades do Integration Hub

O Integration Hub é responsável por:

- Validar os dados recebidos;
- Resolver dependências necessárias para a integração;
- Transformar os dados para o formato esperado pelo sistema de destino;
- Realizar a autenticação;
- Enviar os dados para o provedor externo;
- Registrar o resultado da integração;
- Retornar o resultado da execução.

---

# O que o Integration Hub não faz

O Integration Hub **não mantém histórico** das entidades integradas e **não é responsável por detectar alterações de estado**.

Também não é responsabilidade do framework:

- Detectar mudanças de negócio;
- Decidir quando uma entidade deve ser enviada;
- Sincronizar dados entre sistemas;
- Agendar integrações;
- Controlar o ciclo de vida das entidades.

Essas responsabilidades pertencem exclusivamente ao sistema integrador.

### Exemplo

Se um veículo mudar de proprietário, cabe ao sistema integrador detectar essa alteração e solicitar um novo envio.

O Integration Hub apenas valida a requisição, resolve as dependências, monta o payload e realiza a integração.

---

# Estrutura do Framework

```text
core/
 ├── connector
 ├── context
 ├── dependencies
 ├── pipeline
 ├── result
 ├── validation

modules/
 ├── frotas
 ├── pessoas
 ├── patrimonio
 └── ...
```

---

# Como criar um novo módulo

Cada módulo normalmente possui:

- Request
- Payload
- Response
- Connector
- Validator
- Mapper
- Dependency Resolvers
- Testes unitários

A infraestrutura do Pipeline é reutilizada por todos os módulos, sendo necessário implementar apenas as regras específicas de cada integração.

---

# Estado do Projeto

O framework encontra-se em evolução contínua, com infraestrutura baseada em Pipeline e arquitetura modular já estabelecidas. Novos módulos de integração são adicionados reutilizando a mesma infraestrutura, garantindo baixo acoplamento, alta extensibilidade e facilidade de manutenção.