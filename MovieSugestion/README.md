# 🎬 Movie Suggestion Application

## 📚 Descrição
Esta aplicação é um serviço de sugestão de filmes desenvolvido em Java utilizando Spring Boot. A aplicação faz uso da biblioteca **SpringAI** para integrar com o modelo de linguagem GPT da OpenAI, permitindo interações avançadas com o usuário.

## 🚀 Tecnologias Utilizadas
- Java
- Spring Boot
- Maven
- SpringAI

## ⚙️ Configuração

### Dependências
Adicione a dependência do SpringAI no seu `pom.xml`:
```xml
<dependency>
    <groupId>org.springframework.ai</groupId>
    <artifactId>spring-ai</artifactId>
    <version>1.0.0</version>
</dependency>
```

### Configuração do `application.properties`

Configure a chave da API e o modelo GPT no arquivo src/main/resources/application.properties:

```properties
spring.application.name=MovieSugestion
spring.ai.openai.api-key=xxxx

# qual o modelo de gtp
spring.ai.openai.chat.options.model=gpt-4o-2024-08-06
```

## 📡 Endpoints

### `/movies/informacoes`

Retorna informações sobre filmes.

* **Método:** GET
* **Parâmetros:** `message` (opcional, padrão: "Quais são os top 10 filmes mais assistidos?")
* **Exemplo de Uso:**<pre>curl http://localhost:8080/movies/informacoes?message=Quais%20são%20os%20top%2010%20filmes%20mais%20assistidos? </pre>

### `/movies/reviews`

Retorna um resumo do filme com o nome do diretor e do elenco principal.

* **Método:** GET
* **Parâmetros:** `movie` (opcional, padrão: "Star Wars: The Force Awakens")
* **Exemplo de Uso:**<pre>curl http://localhost:8080/movies/reviews?movie=Star%20Wars:%20The%20Force%20Awakens </pre>

### `/movies/context/ids`

Retorna todos os IDs de contexto.  

* **Método:** GET
* **Exemplo de Uso:**<pre>curl http://localhost:8080/movies/context/ids </pre>

### `/movies/context/informacoes`

Retorna informações de contexto.  

* **Método:** GET
* **Parâmetros:** `contextId` (opcional); `message` (opcional, padrão: "Quais são os top 10 filmes mais assistidos?")
* **Exemplo de Uso:**<pre>curl "http://localhost:8080/movies/context/informacoes?contextId=1234&message=Quais%20são%20os%20top%2010%20filmes%20mais%20assistidos?" </pre>

## 🧠 Implementação de Contexto no Chat

A aplicação utiliza um serviço para gerenciar o contexto das conversas, armazenando o histórico de mensagens e gerando prompts contextuais para o modelo GPT.

### Exemplo de implementação

```java
public class ConversationContextServiceImpl implements ConversationContextService {
    private Map<String, List<String>> contextoStore = new HashMap<>();

    @Override
    public String preparePrompt(String contextId, String message) {
        if (!contextoStore.containsKey(contextId)) {
            contextoStore.put(contextId, new ArrayList<>());
        } else {
            List<String> history = contextoStore.get(contextId);
            history.add(message);
            contextoStore.put(contextId, history);
        }

        StringBuilder prompt = new StringBuilder();
        List<String> history = contextoStore.get(contextId);

        prompt.append(PromptContext.PROMPT_WHAT_WERE_WE_TAKLING_ABOUT);
        for (int i = 0; i < history.size(); i++) {
            prompt.append(tratarHistory(history.get(i)));
        }

        prompt.append(PromptContext.PROMPT_DELIMITER_FOR_HISTORICAL_CONTEXT);
        prompt.append(PromptContext.PROMPT_USE_CONTEXT_IF_NEEDED);
        prompt.append(PromptContext.PROMPT_THE_CURRENT_QUESTION);
        prompt.append(history.get(history.size() - 1));

        return prompt.toString();
    }

    private String tratarHistory(String s) {
        return PromptContext.PROMPT_DELIMITER + s + PromptContext.PROMPT_DELIMITER;
    }
}
```

## 🤝 Contribuições

Contribuições são bem-vindas! Para contribuir, envie um pull request com as suas alterações.

