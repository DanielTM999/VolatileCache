# Volatile Cache DB

Este projeto implementa um sistema de cache em memória utilizando a abordagem de cache volátil, permitindo a execução de operações de inserção, atualização e seleção em áreas específicas de um banco de dados simulado. A estrutura utiliza `ConcurrentHashMap` para garantir a segurança em ambientes de múltiplas threads.

## Estrutura do Projeto

### Pacotes Principais

- **dmt.dynamic.dbcache**: Contém a implementação do cache volátil (`VolatileCacheDb`) e a lógica de inserção, seleção e atualização.
- **dmt.dynamic.core**: Define as interfaces essenciais para operações de consulta e atualização, que são utilizadas por classes concretas.

### Classes e Interfaces

- **VolatileCacheDb**: Implementa a interface `VolatileCache` e gerencia um mapa de áreas, onde cada área é uma lista de objetos.
  
  - **Métodos Principais**:
    - `select(Class<T> element)`: Retorna uma instância de `SelectAreaQuery` para realizar consultas na área especificada.
    - `update(Class<T> element)`: Retorna uma instância de `UpdateAreaQuery` para realizar atualizações na área especificada.
    - `insert(Object entity)`: Retorna uma instância de `InsertAreaQuery` para inserir entidades em uma área.
    - `delete(Class<T> element)`: Retorna uma instância de `DeleteAreaQuery` para realizar remoção em uma área especificada.

- **SelectAreaQuery<T>**: Interface para executar consultas de seleção em uma área específica.
  
- **SelectWhereStruct<T>**: Interface para definir condições de seleção.
  
- **QueryCollectors<T>**: Interface para coletar e manipular resultados de consultas.

- **UpdateAreaQuery<T>**: Interface para executar atualizações em uma área específica.
  
- **UpdateWhereStruct<T>**: Interface para definir condições de atualização.
  
- **UpdateSet<T>**: Interface para realizar a atualização de entidades.

- **DeleteAreaQuery<T>**: Interface para realizar a remoção de entidades.

## Como Usar

1. **Instalação**: Certifique-se de ter o Java instalado em seu ambiente. Clone o repositório e compile o projeto.

1.1 *apos clonar o repositorio*: Instale no repositório .m2

```bash
    mvn clean install
```

1.2 *apos instalar no repositorio .m2*: Adicione a dependência no pom.xml
```xml
    <dependencies>
        <dependency>
            <groupId>dmt.dynamic</groupId>
            <artifactId>volatile_cache</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
```


2. **Criando uma Instância do Cache**:

```java
   VolatileCache cache = new VolatileCacheDb();
```

3. **Inserindo Dados**:
```java
   cache
    .insert(new MyEntity())
    .into("myArea");
```

4. **Selecionando Dados**:

4.1 *Selecionando como lista*:
```java
   List<MyEntity> results = cache
        .select(MyEntity.class)
        .from("myArea")
        .where(entity -> entity.getId() > 0)
    .toList();
```

4.2 *Selecionando apenas 1(primeiro)*:
```java
    MyEntity singleEntity = cache
        .select(MyEntity.class)
        .from("myArea")
        .where(entity -> entity.getId() == 1)
    .single();
```

4.3 *Selecionando Resultado Opcional*:
```java
    Optional<MyEntity> optionalEntity = cache
        .select(MyEntity.class)
        .from("myArea")
        .where(entity -> entity.getId() == 999) 
    .singleOptional();

    if (optionalEntity.isPresent()) {
        MyEntity entity = optionalEntity.get();
        System.out.println("Entidade encontrada: " + entity);
    } else {
        System.out.println("Entidade não encontrada.");
    }
```

4.4 *Contando Resultados*:
```java
    int count = cache
        .select(MyEntity.class)
        .from("myArea")
        .where(entity -> entity.getId() > 0)
    .count();
    System.out.println("Número de entidades: " + count);
```

4.5 *Verificando se a Consulta Está Vazia*:
```java
   boolean isEmpty = cache
        .select(MyEntity.class)
        .from("myArea")
        .where(entity -> entity.getId() < 0)
    .isEmpty();

    if (isEmpty) {
        System.out.println("Nenhuma entidade encontrada com ID negativo.");
    }

```

4.6 *Selecionando Resultado Distintos*:
```java
    List<MyEntity> distinctEntities = cache
        .select(MyEntity.class)
        .from("myArea")
        .where(entity -> entity.getId() > 0)
    .distinct();
```

5. **Atualizando Dados**:
```java
    cache
        .update(MyEntity.class)
        .from("myArea")
        .where(entity -> entity.getId() == 1)
    .update(entity -> entity.setName("Updated Name"));
```

6. **Deletando Dado**:
```java
    cache.delete(MyEntity.class)
            .from("myArea")
            .where(entity -> entity.getId() == 1);
```


7. **Removendo áreas**:
```java
    cache.drop("myArea");
    cache.drop(); // Remove todas as áreas
```