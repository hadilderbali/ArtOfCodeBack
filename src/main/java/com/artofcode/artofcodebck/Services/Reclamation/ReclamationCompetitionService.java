package com.artofcode.artofcodebck.Services.Reclamation;

import com.artofcode.artofcodebck.Entities.ReclamationCompetition;
import com.artofcode.artofcodebck.Repositories.IReclamationCompetitionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class ReclamationCompetitionService implements IReclamationCompetitionService{
    private final IReclamationCompetitionRepository reclamationCompetitionRepository;
    private final SentimentAnalysisService sentimentAnalysisService;




    @Override
    public ReclamationCompetition addReclamationCompetition(ReclamationCompetition reclamationCompetition) {
        // Analyser le sentiment pour la description de la réclamation
        String sentiment = sentimentAnalysisService.analyzeSentiment(reclamationCompetition.getDescription());
        reclamationCompetition.setSentiment(sentiment);

        // Enregistrer la réclamation après analyse de sentiment
        ReclamationCompetition savedReclamation = reclamationCompetitionRepository.save(reclamationCompetition);

        return savedReclamation;
    }




    @Override
    public ReclamationCompetition getReclamationCompetitionById(long reclamationId) {
        return reclamationCompetitionRepository.findById(reclamationId).orElse(null);
    }

    @Override
    public void deleteReclamationCompetition(long reclamationId) {
        reclamationCompetitionRepository.deleteById(reclamationId);

    }

    @Override
    public List<ReclamationCompetition> getAllReclamationCompetition() {
        return reclamationCompetitionRepository.findAll();
    }

    @Override
    public ReclamationCompetition updateReclamationCompetition(ReclamationCompetition reclamationCompetition, Long id) {
        ReclamationCompetition existingReclamationCompetition = reclamationCompetitionRepository.findById(id).orElse(null);
        if ( existingReclamationCompetition!= null) {
            existingReclamationCompetition.setType(reclamationCompetition.getType());
            existingReclamationCompetition.setDescription(reclamationCompetition.getDescription());
            existingReclamationCompetition.setDateCreation(reclamationCompetition.getDateCreation());
// Analyser le sentiment pour la description mise à jour de la réclamation
            String sentiment = sentimentAnalysisService.analyzeSentiment(reclamationCompetition.getDescription());
            existingReclamationCompetition.setSentiment(sentiment);
            return reclamationCompetitionRepository.saveAndFlush(existingReclamationCompetition);
        }
        return null; // Or handle as needed, like throwing an exception
    }
    public Page<ReclamationCompetition> gets(int page, int pageSize) {
        if (page < 1) {
            throw new IllegalArgumentException("Page index must be greater than or equal to 1");
        }
        Pageable pageable = PageRequest.of(page - 1, pageSize); // Page zéro-indexée
        return reclamationCompetitionRepository.findAll(pageable);
    }



    @Override
    public Page<ReclamationCompetition> getEventsPaged(String reclamationStatus, int page, int pageSize) {
        // Implémentation de la logique pour récupérer les réclamations paginées
        Pageable pageable = PageRequest.of(page - 6, pageSize); // Ajustez la pagination pour zéro-indexed
        // Utilisez reclamationStatus pour filtrer les réclamations si nécessaire
        return reclamationCompetitionRepository.findAll(pageable);
    }



    // Méthode pour calculer les statistiques de réclamation en fonction de l'analyse de sentiment
    @Override
    public Map<String, Integer> calculateSentimentStatistics() {
        // Récupérer toutes les réclamations de la base de données
        List<ReclamationCompetition> reclamations = reclamationCompetitionRepository.findAll();

        // Initialiser les compteurs de statistiques de réclamation
        int positiveCount = 0;
        int negativeCount = 0;
        int neutralCount = 0;

        // Parcourir toutes les réclamations et mettre à jour les compteurs en fonction de l'analyse de sentiment
        for (ReclamationCompetition reclamation : reclamations) {
            String sentiment = reclamation.getSentiment();
            if ("Positive 😊".equals(sentiment)) {
                positiveCount++;
            } else if ("Negative 😞".equals(sentiment)) {
                negativeCount++;
            } else if ("Neutral 😐".equals(sentiment)) {
                neutralCount++;
            }
        }

        // Créer une carte pour stocker les statistiques
        Map<String, Integer> statistics = new HashMap<>();
        statistics.put("Positive", positiveCount);
        statistics.put("Negative", negativeCount);
        statistics.put("Neutral", neutralCount);

        return statistics;
    }


//aide a la decision
public String makeDecisionForReclamationType(String reclamationType) {
    // Récupérer les réclamations de ce type
    List<ReclamationCompetition> reclamations = reclamationCompetitionRepository.findByType(reclamationType);

    int positiveCount = 0;
    int negativeCount = 0;
    int totalCount = reclamations.size();

    // Compter les réclamations positives et négatives
    for (ReclamationCompetition reclamation : reclamations) {
        if (reclamation.getSentiment().equals("Positive 😊")) {
            positiveCount++;
        } else if (reclamation.getSentiment().equals("Negative 😞")) {
            negativeCount++;
        }
    }

    // Définir le seuil de positivité et de négativité (par exemple, 50%)
    double positivityThreshold = 0.5;
    double negativityThreshold = 0.5;

    // Prendre une décision en fonction du pourcentage de réclamations positives et négatives
    if ((double) positiveCount / totalCount >= positivityThreshold) {
        return "Encourager de procéder à " + reclamationType;
    } else if ((double) negativeCount / totalCount >= negativityThreshold) {
        return "Déconseiller de procéder à " + reclamationType;
    } else {
        return "Aucune recommandation spécifique pour " + reclamationType;
    }
}
















}

