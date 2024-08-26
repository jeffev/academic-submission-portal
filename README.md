# Portal de Submissão de Trabalhos Acadêmicos

Este projeto é um **Portal de Submissão de Trabalhos Acadêmicos** desenvolvido com **Spring Boot** e **Hibernate**. O sistema permite que alunos enviem trabalhos acadêmicos para uma plataforma online, incluindo upload de arquivos e inserção de informações relacionadas.

## Tecnologias Utilizadas

- **Spring Boot**: Framework para simplificação da configuração e desenvolvimento de aplicações Java.
- **Hibernate**: Implementação de JPA para gerenciamento de persistência.
- **PostgreSQL**: Banco de dados relacional para armazenamento dos dados.
- **PrimeFaces**: Biblioteca de componentes UI para JSF.

## Funcionalidades Implementadas

- **Envio de Trabalhos Acadêmicos**: Permite aos alunos enviar trabalhos acadêmicos, incluindo título, descrição e arquivo.

## Configuração do Projeto

### 1. **Clonando o Repositório**

```bash
git clone https://github.com/jeffev/academic-submission-portal.git
cd academic-submission-portal
```

### 2. **Configuração do Banco de Dados**

Certifique-se de ter o PostgreSQL instalado e um banco de dados criado para o projeto. Atualize as configurações do banco de dados no arquivo `src/main/resources/application.properties`:

```properties
# Configurações do Banco de Dados PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/academic_db
spring.datasource.username=seu_usuario
spring.datasource.password=sua_senha
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

# Configuração do Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 3. **Construindo o Projeto**

Certifique-se de ter o Maven instalado. Execute o seguinte comando para construir o projeto:

```bash
mvn clean package
```

### 4. **Executando a Aplicação**

Execute a aplicação Spring Boot usando o comando Maven:

```bash
mvn spring-boot:run
```

### 5. **Acessando a Aplicação**

Abra um navegador e acesse:

```
http://localhost:8080/submission
```

Você verá o formulário para envio de trabalhos acadêmicos.

## Estrutura do Projeto

- **`src/main/java/com/example`**: Contém o código fonte do projeto.
  - **`controller`**: Controladores para gerenciar as solicitações HTTP.
  - **`model`**: Classes de modelo de dados.
  - **`repository`**: Repositórios para acesso aos dados.
- **`src/main/resources`**: Contém recursos da aplicação.
  - **`application.properties`**: Configurações do Spring Boot e Hibernate.
  - **`templates`**: Contém arquivos XHTML para renderização de páginas JSF.
  - **`static`**: Arquivos estáticos como CSS e JS.

## Contribuindo

Se você deseja contribuir para o projeto, siga estes passos:

1. Faça um fork do repositório.
2. Crie uma branch para sua feature (`git checkout -b feature/feature-name`).
3. Commit suas alterações (`git commit -am 'Add new feature'`).
4. Envie para o repositório remoto (`git push origin feature/feature-name`).
5. Abra um Pull Request.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).

## Contato

Para mais informações, entre em contato com:

- **Nome**: Jefferson Valandro 
- **E-mail**: jeffev123@gmail.com
