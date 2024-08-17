package es.minsait.MovieSugestion.prompts;

public class PromptContext {

    public static final String PROMPT_DELIMITER = " <AlphaCrest1407> ";
    public static final String PROMPT_WHAT_WERE_WE_TAKLING_ABOUT = "Estamos falando sobre essas coisas até agora. ";
    public static final String PROMPT_DELIMITER_FOR_HISTORICAL_CONTEXT = "Observe que as perguntas anteriores são delimitadas pela string " + PROMPT_DELIMITER;
    public static final String PROMPT_USE_CONTEXT_IF_NEEDED = "Use as informações contextuais sobre as coisas discutidas até agora somente se for " +
            "necessário para responder sobre a pergunta atual, caso contrário, ignore a discussão histórica até a gora, " +
            "responda ou reconheça com seu conhecimento. Além disso, não inclua o " + PROMPT_DELIMITER + " na sua resposta!";
    public static final String PROMPT_THE_CURRENT_QUESTION = "Agora, aqui está sua pergunta: ";

    public PromptContext() { }
}
