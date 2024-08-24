package es.com.minsait.LangchainDemo.controller;

import dev.langchain4j.model.chat.ChatLanguageModel;
import es.com.minsait.LangchainDemo.dto.MyQuestion;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OpenAIController {

    private ChatLanguageModel chatLanguageModel;

    public OpenAIController(ChatLanguageModel chatLanguageModel) {
        this.chatLanguageModel = chatLanguageModel;
    }

    @PostMapping("/chat")
    public String chatComOpenAI(@RequestBody MyQuestion question){
        return chatLanguageModel.generate(question.question());
    }

}
