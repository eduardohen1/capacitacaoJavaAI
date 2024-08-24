package es.com.minsait.LangchainDemo.controller;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.image.ImageModel;
import dev.langchain4j.model.input.Prompt;
import dev.langchain4j.model.input.structured.StructuredPromptProcessor;
import dev.langchain4j.model.openai.OpenAiImageModel;
import es.com.minsait.LangchainDemo.dto.MyQuestion;
import es.com.minsait.LangchainDemo.dto.StructuredTemplate;
import es.com.minsait.LangchainDemo.rag.Assistant;
import es.com.minsait.LangchainDemo.rag.RAGConfiguration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
public class OpenAIController {

    @Value("${OPENAI_API_KEY}")
    private String apiKey;
    private ChatLanguageModel chatLanguageModel;
    private RAGConfiguration ragConfiguration;
    private Assistant assistant;

    public OpenAIController(ChatLanguageModel chatLanguageModel,
                            RAGConfiguration ragConfiguration) {
        this.chatLanguageModel = chatLanguageModel;
        this.ragConfiguration = ragConfiguration;
    }

    @PostMapping("/chat")
    public String chatComOpenAI(@RequestBody MyQuestion question){
        return chatLanguageModel.generate(question.question());
    }

    @GetMapping("/receita")
    public String facaUmaReceita(){
        StructuredTemplate template = new StructuredTemplate();
        StructuredTemplate.PromptDeReceita rcPrompt = new StructuredTemplate.PromptDeReceita();
        /*rcPrompt.prato = "Sopa";
        rcPrompt.ingredientes = Arrays.asList("cebola", "alho", "sal", "pimenta", "agua",
                "batata", "cenoura", "abobora", "macarrao");*/

        rcPrompt.prato = "Assado";
        rcPrompt.ingredientes = Arrays.asList("frango", "batata", "queijo", "cenoura");

        Prompt prompt = StructuredPromptProcessor.toPrompt(rcPrompt);
        return chatLanguageModel.generate(prompt.text());

    }

    @PostMapping("/chatrag")
    public String chatComMemoriaRAG(@RequestBody MyQuestion question){
        try {
            if(assistant == null)
                assistant = ragConfiguration.configure();
            return assistant.answer(question.question());
        }catch (Exception ex){
            return "Erro: " + ex.getMessage();
        }
    }

    @PostMapping("/imagem")
    public String generateImage(@RequestBody MyQuestion question){
        try{
            ImageModel imageModel = OpenAiImageModel.withApiKey(apiKey);
            return imageModel.generate(question.question())
                    .content().url().toURL().toString();
        }catch (Exception ex){
            return "Erro: " + ex.getMessage();
        }
    }


}
