package es.minsait.MovieSugestion.controller;

import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieAssistantController {

    private OpenAiChatModel openAiChatModel;

    public MovieAssistantController(OpenAiChatModel openAiChatModel) {
        this.openAiChatModel = openAiChatModel;
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

}
