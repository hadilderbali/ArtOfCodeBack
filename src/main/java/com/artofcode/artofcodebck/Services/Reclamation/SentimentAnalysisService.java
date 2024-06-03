package com.artofcode.artofcodebck.Services.Reclamation;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import org.springframework.stereotype.Service;

@Service
public class SentimentAnalysisService {

  private final StanfordCoreNLP stanfordCoreNLP;

  public SentimentAnalysisService(StanfordCoreNLP stanfordCoreNLP) {
    this.stanfordCoreNLP = stanfordCoreNLP;
  }

  public String analyzeSentiment(String text) {
    CoreDocument coreDocument = new CoreDocument(text);
    stanfordCoreNLP.annotate(coreDocument);

    int positiveCount = 0;
    int negativeCount = 0;

    int neutralCount = 0; // Nouveau compteur pour le sentiment neutre
    for (CoreSentence sentence : coreDocument.sentences()) {
      String sentiment = sentence.sentiment();
      if (sentiment.equals("Positive") || sentiment.equals("Very positive")) {
        positiveCount++;
      } else if (sentiment.equals("Negative") || sentiment.equals("Very negative")) {
        negativeCount++;
      } else { // Si le sentiment est neutre
        neutralCount++;
      }
    }

    // Comparer les compteurs pour déterminer le sentiment global
    if (positiveCount > negativeCount && positiveCount > neutralCount) {
      return "Positive 😊";
    } else if (negativeCount > positiveCount && negativeCount > neutralCount) {
      return "Negative 😞";
    } else {
      return "Neutral 😐";
    }
  }
}