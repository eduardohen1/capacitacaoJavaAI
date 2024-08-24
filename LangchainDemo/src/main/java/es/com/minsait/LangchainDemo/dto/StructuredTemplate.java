package es.com.minsait.LangchainDemo.dto;

import dev.langchain4j.model.input.structured.StructuredPrompt;

import java.util.List;

public class StructuredTemplate {

    @StructuredPrompt({
            "Crie uma receita de {{prato}} que possa ser preparada usando somente {{ingredientes}}, ",
            "Estruture a sua resposta da seguinte forma: ",
            "Nome da Receita: ...",
            "Descricao: ...",
            "Tempo de preparo: ...",
            "Ingredientes Necessarios: ",
            "- ...",
            "- ...",
            "Modo de preparo: ",
            "- ...",
            "- ..."
    })
    public static class PromptDeReceita {
        public String prato;
        public List<String> ingredientes;
    }

}
