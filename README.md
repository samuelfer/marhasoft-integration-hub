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

O Integration Hub foi projetado para que novos módulos reutilizem toda a infraestrutura existente. Na maioria dos casos, a criação de um novo módulo consiste apenas em implementar os componentes específicos da integração.

## Estrutura recomendada

Os módulos devem ser organizados por domínio de negócio. Cada módulo agrupa as entidades relacionadas e cada entidade contém todos os componentes necessários para sua integração.

```text
modules/
└── frotas/
    ├── TceFrotasClient.java
    │
    └── veiculo/
        ├── client/
        │   ├── PessoaClient.java
        │   └── PessoaClientResponse.java
        │
        ├── model/
        │   ├── VeiculoItemPayload.java
        │   ├── VeiculoItemRequest.java
        │   ├── VeiculoPayload.java
        │   ├── VeiculoRequest.java
        │   └── VeiculoResponse.java
        │
        ├── resolver/
        │   ├── LocadorDependencyResolver.java
        │   └── ProprietarioDependencyResolver.java
        │
        ├── validation/
        │   ├── VeiculoValidationCode.java
        │   └── VeiculoValidator.java
        │
        ├── VeiculoConnector.java
        ├── VeiculoController.java
        ├── VeiculoDependencies.java
        ├── VeiculoMapper.java
        └── VeiculoService.java
```

Cada entidade deve conter todos os componentes necessários para sua integração, mantendo uma organização coesa e de fácil manutenção.

- **client**: clientes responsáveis pela comunicação com serviços externos específicos da entidade.
- **model**: modelos utilizados durante a integração, como `Request`, `Payload` e `Response`.
- **resolver**: implementações dos `DependencyResolvers` responsáveis por resolver dependências antes da integração.
- **validation**: validadores e códigos de validação da entidade.
- **Demais classes**: componentes principais da integração, como `Connector`, `Mapper`, `Service` e `Controller`.

Os recursos compartilhados entre todas as entidades do módulo, como clientes HTTP comuns, devem permanecer diretamente no pacote do módulo (`modules/frotas`), evitando duplicação entre entidades.
```

---

## 1. Criar o Request

O Request representa os dados recebidos pelo Integration Hub.

Exemplo:

```java
public class VeiculoRequest {

    private String placa;

    private String renavam;

    private Pessoa proprietario;

}
```

O Request deve conter apenas as informações necessárias para a integração.

---

## 2. Criar o Payload

O Payload representa exatamente o formato esperado pelo sistema de destino.

Exemplo:

```java
public class VeiculoPayload {

    private String numeroPlaca;

    private String codigoProprietario;

}
```

É comum que Request e Payload possuam estruturas diferentes.

---

## 3. Criar o Response

O Response representa o retorno da integração.

```java
public class VeiculoResponse {

    private String protocolo;

    private String mensagem;

}
```

---

## 4. Criar o Connector

Todo módulo deve implementar um Connector.

```java
@Component
public class VeiculoConnector
        implements IntegrationConnector<
            VeiculoRequest,
            VeiculoPayload,
            VeiculoResponse> {

}
```

O Connector é responsável por:

- informar seus metadados;
- validar a requisição;
- realizar o mapeamento;
- enviar os dados ao sistema externo.

---

## 5. Implementar a validação

Toda validação deve ser realizada através de um Validator.

```java
@Component
public class VeiculoValidator {

    public ValidationResult validate(VeiculoRequest request) {

        ValidationResult result = new ValidationResult();

        ...

        return result;
    }

}
```

As validações devem utilizar apenas os dados da requisição atual.

---

## 6. Implementar o Mapper

O Mapper transforma o Request no Payload esperado pelo destino.

```java
@Component
public class VeiculoMapper {

    public VeiculoPayload map(VeiculoRequest request) {

        ...

    }

}
```

Nenhuma regra de negócio deve ficar no Mapper.

Sua única responsabilidade é transformar objetos.

---

## 7. Criar os Dependency Resolvers

Algumas integrações dependem da existência prévia de outras entidades no sistema de destino.

Nesses casos, a responsabilidade do **DependencyResolver** é garantir que essas dependências existam antes do envio da entidade principal.

Cada resolver deve possuir **uma única responsabilidade**, sendo responsável por resolver apenas uma dependência.

### Exemplo

Na integração de **Veículo**, o TCE exige que o **Proprietário** já esteja cadastrado.

O fluxo ocorre da seguinte forma:

```
Veículo
    │
    ▼
Proprietário existe no TCE?
    │
 ┌──┴──┐
 │     │
Sim    Não
 │      │
 │      ▼
 │  Integrar Proprietário
 │      │
 └──────┘
    │
    ▼
Enviar Veículo
```

Nesse cenário, cria-se um resolver específico:

```java
@Component
public class ProprietarioDependencyResolver
        implements DependencyResolver<
                VeiculoRequest,
                VeiculoPayload,
                VeiculoResponse> {

    @Override
    public DependencyKey getDependencyKey() {
        return DependencyKey.of(
                IntegrationModule.FROTAS,
                IntegrationOperation.VEICULO,
                IntegrationAction.CREATE);
    }

    @Override
    public void resolve(IntegrationContext<...> context) {

        // Verifica se o proprietário já existe
        // Caso não exista, realiza sua integração
    }

}
```

Da mesma forma, caso o veículo seja locado, pode existir um resolver responsável pelo Locador.

Cada dependência deve possuir seu próprio resolver.

Exemplos:

- Proprietário
- Locador
- Unidade Gestora
- Credor
- Fornecedor

O Integration Hub identifica automaticamente todos os `DependencyResolvers` compatíveis com a operação em execução e os executa antes do mapeamento e do envio da entidade principal.

---

## 8. Registrar os componentes do módulo

Após implementar os componentes da integração, é necessário garantir que o framework consiga localizá-los e utilizá-los durante a execução do Pipeline.

Na maioria dos casos, isso acontece automaticamente através dos mecanismos de injeção de dependências do Spring.

### Connector

O **Connector** é o componente central de uma integração. Ele representa o ponto de entrada do módulo e coordena todo o processo de integração, delegando a validação, o mapeamento e o envio para seus respectivos componentes. É também através dele que o Integration Hub identifica qual integração será executada.

O Connector deve ser registrado como um Bean do Spring.

```java
@Component
public class VeiculoConnector
        implements IntegrationConnector<...> {

}
```

### Dependency Resolvers

Todos os `DependencyResolvers` também devem ser registrados como Beans.

```java
@Component
public class ProprietarioDependencyResolver
        implements DependencyResolver<...> {

}
```

Durante a inicialização da aplicação, o Integration Hub identifica automaticamente todos os resolvers registrados e os organiza por:

- Módulo
- Operação
- Ação

Assim, quando uma integração de **Veículo** for executada, apenas os resolvers compatíveis com essa operação serão chamados.

### Configurações específicas

Caso a integração necessite de configurações adicionais, como:

- clientes HTTP;
- autenticação;
- URLs;
- timeouts;
- APIs externas;

essas configurações devem ser declaradas em uma classe `@Configuration` do módulo.

Exemplo:

```java
@Configuration
public class FrotasConfiguration {

    @Bean
    public TceFrotasClient tceFrotasClient(...) {
        ...
    }

}
```
Dessa forma, o módulo permanece isolado e todas as suas dependências ficam centralizadas em sua própria configuração.

---

## 9. Implementar os testes

Todo módulo deve possuir testes unitários.

Os testes mínimos recomendados são:

- Validator
- Mapper
- Connector
- Cada DependencyResolver

A infraestrutura (Pipeline, Steps e Registry) já é testada pelo framework e não precisa ser reimplementada.

---

# Boas práticas

- Cada classe deve possuir apenas uma responsabilidade.
- Nunca coloque regras de negócio no Mapper.
- Nunca acesse sistemas externos dentro do Validator.
- Utilize DependencyResolvers para resolver dependências.
- Reutilize o Pipeline existente.
- Evite acoplamento entre módulos.

---

# Fluxo de execução

Uma integração percorre sempre o mesmo fluxo:

```
Request
    │
    ▼
Validation
    │
    ▼
Dependency Resolution
    │
    ▼
Mapping
    │
    ▼
Authentication
    │
    ▼
Transport
    │
    ▼
Audit
    │
    ▼
Response
```

Os módulos implementam apenas os componentes específicos de cada etapa. Toda a orquestração é realizada automaticamente pelo Integration Hub.

---

# Princípios de Desenvolvimento

Ao implementar novos módulos, siga os seguintes princípios:

- O Integration Hub é stateless.
- O Integration Hub não mantém histórico das entidades.
- O Integration Hub não decide quando uma entidade deve ser enviada.
- Cada componente deve possuir uma única responsabilidade.
- Regras de negócio pertencem ao sistema integrador, exceto aquelas necessárias para garantir a consistência da integração.
- Toda dependência deve ser resolvida por um DependencyResolver.
- O Pipeline é reutilizado por todos os módulos.
---

# Estado do Projeto

O framework encontra-se em evolução contínua, com infraestrutura baseada em Pipeline e arquitetura modular já estabelecidas. Novos módulos de integração são adicionados reutilizando a mesma infraestrutura, garantindo baixo acoplamento, alta extensibilidade e facilidade de manutenção.