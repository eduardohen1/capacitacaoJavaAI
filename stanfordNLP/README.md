# 📚 Aplicação de Processamento de Linguagem Natural com StanfordNLP

Esta aplicação demonstra o uso da biblioteca StanfordNLP para realizar diversas tarefas de Processamento de Linguagem Natural (PLN) em textos. A aplicação está dividida em três exemplos principais: Tokenização, Análise de Sentimentos e Análise de Partes do Discurso (POS).

## 🚀 Tecnologias Utilizadas

- Java
- Maven
- StanfordNLP

## 📂 Configuração do Projeto

### Dependências no `pom.xml`

Para utilizar a biblioteca StanfordNLP, adicione as seguintes dependências no seu arquivo `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>edu.stanford.nlp</groupId>
        <artifactId>stanford-corenlp</artifactId>
        <version>4.2.2</version>
    </dependency>
    <dependency>
        <groupId>edu.stanford.nlp</groupId>
        <artifactId>stanford-corenlp</artifactId>
        <version>4.2.2</version>
        <classifier>models</classifier>
    </dependency>
</dependencies>
```

### 📦 Configuração do Pipeline

O arquivo Pipeline.java é responsável por configurar o pipeline do StanfordNLP. Ele garante que todas as anotações necessárias sejam carregadas.

```java
package es.com.minsait;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;

import java.util.Properties;

public class Pipeline {

    private static StanfordCoreNLP stanfordCoreNLP;

    private Pipeline() {
    }

    public static StanfordCoreNLP getInstance() {
        if (stanfordCoreNLP == null) {
            Properties properties = new Properties();
            properties.setProperty("annotators", "tokenize,ssplit,pos,lemma,ner,parse,sentiment");
            stanfordCoreNLP = new StanfordCoreNLP(properties);
        }
        return stanfordCoreNLP;
    }
}
```

### 🧠 Execução dos Exemplos

#### Tokenização

O exemplo de tokenização divide um texto em palavras individuais (tokens).

#### Código
```java
package es.com.minsait.example;

import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import es.com.minsait.Pipeline;

import java.util.List;

public class Tokenize {
    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getInstance();
        String text = "Hey! This is Eduardo Henrique.";

        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreLabel> coreLabelList = coreDocument.tokens();
        for(CoreLabel coreLabel : coreLabelList){
            System.out.println(coreLabel.originalText());
        }
    }
}
```

#### Resultado:

```
Hey
!
This
is
Eduardo
Henrique
.
```

#### Análise de Sentimentos

O exemplo de análise de sentimentos classifica cada sentença em um texto com base no sentimento (positivo, negativo, neutro).

#### Código
```java
package es.com.minsait.example;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import es.com.minsait.Pipeline;

import java.util.List;

public class SentimentAnalysis {
    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getInstance();
        String text = "Hello this is Eduardo. I don´t like this movie. I hate this movie.";

        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);

        List<CoreSentence> sentences = coreDocument.sentences();
        for(CoreSentence sentence : sentences){
            String sentiment = sentence.sentiment();
            System.out.println(sentence + " = " + sentiment);
        }
    }
}
```

#### Resultado:

```
Hello this is Eduardo. = Neutral
I don´t like this movie. = Negative
I hate this movie. = Very Negative
```

#### Análise de Partes do Discurso (POS)

O exemplo de POS identifica a função gramatical de cada palavra em uma sentença.

#### Código
```java
package es.com.minsait.example;

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import es.com.minsait.Pipeline;

import java.util.List;

public class POS {
    public static void main(String[] args) {
        StanfordCoreNLP stanfordCoreNLP = Pipeline.getInstance();
        String text = "Hey! This is Eduardo Henrique.";

        CoreDocument coreDocument = new CoreDocument(text);
        stanfordCoreNLP.annotate(coreDocument);
        List<CoreLabel> coreLabelList = coreDocument.tokens();
        for(CoreLabel coreLabel : coreLabelList){
            String pos = coreLabel.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            System.out.println(coreLabel.originalText() + " = " + pos);
        }
    }
}
```

#### Resultado:

```
Hey = UH
! = .
This = DT
is = VBZ
Eduardo = NNP
Henrique = NNP
. = .
```

## 🤝 Contribuições

Contribuições são bem-vindas! Para contribuir, envie um pull request com as suas alterações.