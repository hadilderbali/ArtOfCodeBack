package com.artofcode.artofcodebck.Services.Reclamation;

import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;
@Configuration
public class Pipeline {

    @Bean
    public StanfordCoreNLP stanfordCoreNLP() {
        Properties properties = new Properties();
        properties.setProperty("annotators", "tokenize, ssplit, pos, parse, sentiment");
        return new StanfordCoreNLP(properties);
    }
}