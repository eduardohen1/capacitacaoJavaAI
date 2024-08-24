package es.com.minsait.example;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import es.com.minsait.Pipeline;

import java.util.List;

public class Lemma {
    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getInstance();
        String text = "Hey! This is Eduardo Henrique. I am a software engineer. I love to code. I am learning new things.";

        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreLabel> coreLabelList = coreDocument.tokens();
        for(CoreLabel coreLabel : coreLabelList){
            String lemma = coreLabel.lemma();
            System.out.println(coreLabel.originalText() + " = " + lemma);
        }
    }
}
