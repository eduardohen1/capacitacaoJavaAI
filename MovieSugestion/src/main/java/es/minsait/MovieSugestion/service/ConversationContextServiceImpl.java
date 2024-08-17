package es.minsait.MovieSugestion.service;

import es.minsait.MovieSugestion.prompts.PromptContext;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ConversationContextServiceImpl implements ConversationContextService {

    Map<String, List<String>> contextoStore = new HashMap<>();

    @Override
    public List<String> fetchAllContextId() {
        return new ArrayList<>(contextoStore.keySet());
    }

    @Override
    public String preparePrompt(String contextId, String message) {
        if(!contextoStore.containsKey(contextId)) {
            List<String> history = new ArrayList<>();
            history.add(message);
            contextoStore.put(contextId, history);
            return message;
        }else{
            List<String> history = contextoStore.get(contextId);
            history.add(message);
            contextoStore.put(contextId, history);
        }

        StringBuilder prompt = new StringBuilder();
        List<String> history = contextoStore.get(contextId);

        //trabalhar a criação de prompt
        prompt.append(PromptContext.PROMPT_WHAT_WERE_WE_TAKLING_ABOUT);
        for(int i = 0; i < history.size(); i++) {
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
