package es.minsait.MovieSugestion.controller;

import es.minsait.MovieSugestion.service.ConversationContextService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/movies")
public class MovieAssistantController {

    private OpenAiChatModel openAiChatModel;
    private ConversationContextService conversationContextService;

    public MovieAssistantController(OpenAiChatModel openAiChatModel,
                                    ConversationContextService conversationContextService) {
        this.openAiChatModel = openAiChatModel;
        this.conversationContextService = conversationContextService;
    }

    @GetMapping("/informacoes") // http://localhost:8080/movies/informacoes
    public String getInformacoes(
            @RequestParam(value="message", defaultValue="Quais são os top 10 filmes mais assistidos?")
            String message) {
        return openAiChatModel.call(message);
    }

    @GetMapping("/informacoes/chatresponse") // http://localhost:8080/movies/informacoes/chatresponse
    public ChatResponse getInformacoesChatResponse(
            @RequestParam(value="message", defaultValue="Quais são os top 10 filmes mais assistidos?")
            String message) {
       Prompt prompt = new Prompt(message);
       return openAiChatModel.call(prompt);
    }

    //criar template de prompt
    @GetMapping("/reviews") // http://localhost:8080/movies/reviews
    public String getReviews(
            @RequestParam(value="movie", defaultValue="Star Wars: The Force Awakens")
            String movie) {
        PromptTemplate promptTemplate = new PromptTemplate(
                """
                Forneca um resumo do filme {movie}
                com o nome do diretor e do elenco principal.
                O texto com o nome do diretor e do elenco, crie lista de itens.
                """
        );
        promptTemplate.add("movie", movie);
        return openAiChatModel.call(promptTemplate.create())
                .getResult()
                .getOutput()
                .getContent();
    }

    //end point de buscar os ids de contexto
    @GetMapping("/context/ids") // http://localhost:8080/movies/context/ids
    public List<String> getContextIds() {
        return conversationContextService.fetchAllContextId();
    }

    // end point de informacao de contexto
    @GetMapping("/context/informacoes") // http://localhost:8080/movies/context/informacoes
    public Map<String, String> getContextInformacoes(
            @RequestParam(value="contextId", defaultValue = "") String contextId,
            @RequestParam(value="message", defaultValue = "Quais são os top 10 filmes mais assistidos?") String message
    ){
        if(contextId.isEmpty())
            contextId = UUID.randomUUID().toString();
        String prompt = conversationContextService.preparePrompt(contextId, message);
        return Map.of("Generated", openAiChatModel.call(prompt));
    }

}
