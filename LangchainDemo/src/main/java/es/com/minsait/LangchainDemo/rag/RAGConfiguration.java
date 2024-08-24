package es.com.minsait.LangchainDemo.rag;

import ai.djl.util.Utils;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.List;

@Service
public class RAGConfiguration {

    @Value("${OPENAI_API_KEY}")
    private String apiKey;

    public Assistant configure() throws Exception {

        // carregar o documento
        List<Document> documents;
        documents = FileSystemDocumentLoader.loadDocuments(
                toPath("documents/"),
                glob("*.txt")
        );

        /**
         * Criar o assitente:
         * 1) chat model
         * 2) memoria de chat
         * 3) elemento de recuperecao de conteudo
         */
        Assistant assistant = AiServices.builder(Assistant.class)
                .chatLanguageModel(OpenAiChatModel.withApiKey(apiKey))
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .contentRetriever(createContentRetriever(documents))
                .build();

        return assistant;
    }

    public ContentRetriever createContentRetriever(List<Document> documents){
        //pegar estes documentos e criar um Embedded Store em memória
        InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        //converter todos os tokens do docuemnto em vetores numericos
        EmbeddingStoreIngestor.ingest(documents, embeddingStore);
        //cria efetivamente o ContentRetriever a partir da embedding store
        return EmbeddingStoreContentRetriever.from(embeddingStore);
    }

    public Path toPath(String caminho){
        try{
            URL fileUrl = Utils.class.getClassLoader()
                    .getResource(caminho);
            return Paths.get(fileUrl.toURI());
        }catch (URISyntaxException ex){
            throw new RuntimeException(ex);
        }
    }

    public PathMatcher glob(String glob){
        return FileSystems.getDefault()
                .getPathMatcher("glob:" + glob);
    }

}
