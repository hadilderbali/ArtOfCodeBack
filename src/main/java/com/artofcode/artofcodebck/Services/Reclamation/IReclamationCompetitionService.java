package com.artofcode.artofcodebck.Services.Reclamation;

import com.artofcode.artofcodebck.Entities.ReclamationCompetition;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface IReclamationCompetitionService {
 ReclamationCompetition addReclamationCompetition(ReclamationCompetition reclamationCompetition);

 ReclamationCompetition getReclamationCompetitionById(long reclamationId);

 void deleteReclamationCompetition(long reclamationId);

 List<ReclamationCompetition> getAllReclamationCompetition();


 ReclamationCompetition updateReclamationCompetition(ReclamationCompetition reclamationCompetition, Long id);

 Page<ReclamationCompetition> getEventsPaged(
         String reclamationStatus,
         int page,
         int pageSize
 );

 Page<ReclamationCompetition> gets(int page, int pageSize);
// String analyzeSentimentForReclamation(ReclamationCompetition reclamation);
 //void sendNotificationForNegativeSentiment(ReclamationCompetition reclamation);

 Map<String, Integer> calculateSentimentStatistics();
 String makeDecisionForReclamationType(String reclamationType);
}