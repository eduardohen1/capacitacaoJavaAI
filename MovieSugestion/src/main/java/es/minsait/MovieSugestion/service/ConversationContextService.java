package es.minsait.MovieSugestion.service;

import java.util.List;

public interface ConversationContextService {

    List<String> fetchAllContextId();
    String preparePrompt(final String contextId, final String message);

}
