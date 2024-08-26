# 📚 Langchain4j Demo Project

Este projeto é uma aplicação web que utiliza Spring Boot no backend e Thymeleaf no frontend. A aplicação permite que os usuários façam perguntas através de um campo de texto em uma página web. Essas perguntas são enviadas para o servidor backend, que processa a pergunta e retorna uma resposta. A resposta é então exibida na página web utilizando um efeito de máquina de escrever.

## 🚀 Tecnologias Utilizadas

- Java
- Spring Boot
- Maven
- Thymeleaf
- JavaScript

## 📂 Estrutura do Projeto

- `src/main/java/es/com/minsait/LangchainDemo` - Código fonte Java
- `src/main/resources/templates` - Templates Thymeleaf
- `src/main/resources/static` - Arquivos estáticos (JavaScript, CSS)

## 🌐 Endpoints

### `OpenAIController`

#### `POST /chat`

Este endpoint recebe uma pergunta do usuário e retorna uma resposta processada.

**Exemplo de Requisição:**

```json
{
  "question": "Qual é a capital da França?"
}
```

**Exemplo de Resposta:**

```json
{
  "answer": "A capital da França é Paris."
}
```

## 📦 DTOs

#### `QuestionDTO`

Este DTO é utilizado para encapsular a pergunta enviada pelo usuário.
```java
public class QuestionDTO {
    private String question;

    // Getters e Setters
}
```

#### `AnswerDTO`

Este DTO é utilizado para encapsular a resposta retornada pelo servidor.

```java
public class AnswerDTO {
    private String answer;

    // Getters e Setters
}
```

## 🧠 Langchain4j e Processo de RAG

### O que é Langchain4j?

Langchain4j é uma biblioteca que facilita a integração com modelos de linguagem, permitindo a construção de aplicações que utilizam processamento de linguagem natural (NLP).

### Processo de RAG (Retrieval-Augmented Generation)

O processo de RAG envolve duas etapas principais:

1. **Recuperação (Retrieval)**: Busca de informações relevantes em uma base de dados ou documentos.
2. **Geração (Generation)**: Geração de uma resposta baseada nas informações recuperadas.

### Exemplo de Uso

#### Configuração do Langchain4j
```java
@Configuration
public class LangchainConfig {

    @Bean
    public LangchainService langchainService() {
        return new LangchainService();
    }
}
```

#### Implementação do RAG

```java
@RestController
@RequestMapping("/chat")
public class OpenAIController {

    @Autowired
    private LangchainService langchainService;

    @PostMapping
    public ResponseEntity<AnswerDTO> getAnswer(@RequestBody QuestionDTO questionDTO) {
        String question = questionDTO.getQuestion();
        String answer = langchainService.processQuestion(question);
        AnswerDTO answerDTO = new AnswerDTO();
        answerDTO.setAnswer(answer);
        return ResponseEntity.ok(answerDTO);
    }
}
```

#### Serviço de Langchain

```java
@Service
public class LangchainService {

    public String processQuestion(String question) {
        // Etapa de Recuperação
        String retrievedInfo = retrieveInformation(question);

        // Etapa de Geração
        String generatedAnswer = generateAnswer(retrievedInfo);

        return generatedAnswer;
    }

    private String retrieveInformation(String question) {
        // Implementação da lógica de recuperação
        return "Informação recuperada baseada na pergunta: " + question;
    }

    private String generateAnswer(String retrievedInfo) {
        // Implementação da lógica de geração
        return "Resposta gerada baseada na informação: " + retrievedInfo;
    }
}
```

## 🌐 Interface Web

### Consumo do Endpoint `\chat`


A aplicação web permite que os usuários façam perguntas através de um campo de texto na página principal. Essas perguntas são enviadas para o endpoint /chat e a resposta é exibida na página utilizando um efeito de máquina de escrever.

#### Exemplo de Uso:

1. Acesse `http://localhost:8080` no seu navegador.
2. Digite sua pergunta no campo de texto.
3. Clique no botão "Perguntar".
4. A resposta será exibida na página.

## 🖥️ Executando o Projeto

1. Clone o repositório.
2. Navegue até o diretório do projeto.
3. Execute o comando `mvn spring-boot:run` para iniciar a aplicação.
Acesse `http://localhost:8080` no seu navegador.

## 🤝 Contribuições

Contribuições são bem-vindas! Para contribuir, envie um pull request com as suas alterações.