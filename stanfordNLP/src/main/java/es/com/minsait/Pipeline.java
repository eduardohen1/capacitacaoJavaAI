package es.com.minsait;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class Pipeline {

    private static Properties properties;
    private static String propertiesName = "tokenize, ssplit, pos, lemma, ner, parse, sentiment"; //os nomes dos métodos de pesquisa.
    private static StanfordCoreNLP stanfordCoreNLP;

    private Pipeline() { }

    static {
        properties = new Properties();
        properties.setProperty("annotators", propertiesName);
    }

    public static StanfordCoreNLP getInstance(){
        if(stanfordCoreNLP == null)
            stanfordCoreNLP = new StanfordCoreNLP(properties);
        return  stanfordCoreNLP;
    }


}
