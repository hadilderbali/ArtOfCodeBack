package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Entities.ReclamationCompetition;
import com.artofcode.artofcodebck.Repositories.IReclamationCompetitionRepository;
import com.artofcode.artofcodebck.Services.Reclamation.IReclamationCompetitionService;
import com.artofcode.artofcodebck.Services.Reclamation.SentimentAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:4200/api")
@RequestMapping("/reclamation")
public class ReclamationCompetitionRestController {
    private final IReclamationCompetitionService reclamationCompetitionService;
    private final SentimentAnalysisService sentimentAnalysisService; // Injection du service d'analyse de sentiment
    private final IReclamationCompetitionRepository reclamationCompetitionRepository; // Ajoutez cette ligne


    @GetMapping("/getAllReclamations")
    public ResponseEntity<List<ReclamationCompetition>> getAllReclamations() {
        List<ReclamationCompetition> reclamations = reclamationCompetitionService.getAllReclamationCompetition();
        return ResponseEntity.ok(reclamations);
    }
    @PostMapping("/addReclamation")

    public ResponseEntity<?> addReclamation(@RequestBody ReclamationCompetition reclamation) {
        try {
            // Analyser le sentiment de la réclamation
            String sentiment = sentimentAnalysisService.analyzeSentiment(reclamation.getDescription());
            reclamation.setSentiment(sentiment);

            // Enregistrer la réclamation
            ReclamationCompetition savedReclamation = reclamationCompetitionService.addReclamationCompetition(reclamation);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedReclamation);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while processing the request: " + e.getMessage());
        }
    }


    @GetMapping("/getReclamationCompetitionById/{idReclamation}")
    public ReclamationCompetition getReclamationCompetitionById(@PathVariable long idReclamation) {
        return reclamationCompetitionService.getReclamationCompetitionById(idReclamation);

    }
    @PutMapping("/updateReclamationCompetition/{id}")
    public ResponseEntity<ReclamationCompetition> updateReclamationCompetition(
            @RequestBody ReclamationCompetition reclamationCompetition,
            @PathVariable("id") Long id) {

        ReclamationCompetition updatedReclamation = reclamationCompetitionService.updateReclamationCompetition(reclamationCompetition, id);

        if (updatedReclamation != null) {
            // Retourner la réclamation mise à jour avec le statut HTTP OK
            return ResponseEntity.ok(updatedReclamation);
        } else {
            // Si la réclamation n'est pas trouvée, retourner le statut HTTP Not Found
            return ResponseEntity.notFound().build();
        }
    }
    @DeleteMapping("/delete/{idReclamation}")
    public void deleteReclamationCompetition(@PathVariable long idReclamation) {

        reclamationCompetitionService.deleteReclamationCompetition(idReclamation);
    }

    @GetMapping("/paged")
    public ResponseEntity<Page<ReclamationCompetition>> getEventsPaged(
            @RequestParam(required = false) String reclamationStatus,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<ReclamationCompetition> pageOfReclamations = reclamationCompetitionService.getEventsPaged(reclamationStatus, page, pageSize);
        return ResponseEntity.ok(pageOfReclamations);
    }
    @GetMapping("/pagedd")
    public ResponseEntity<Page<ReclamationCompetition>> getEventsPagedd(
            @RequestParam(required = false) String reclamationStatus,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ) {
        Page<ReclamationCompetition> pageOfReclamations = reclamationCompetitionService.gets(page, pageSize);
        return ResponseEntity.ok(pageOfReclamations);
    }

    // Méthode pour récupérer les statistiques de réclamation en fonction de l'analyse de sentiment
    @GetMapping("/sentiment-statistics")
    public ResponseEntity<Map<String, Integer>> getSentimentStatistics() {
        Map<String, Integer> sentimentStatistics = reclamationCompetitionService.calculateSentimentStatistics();
        return ResponseEntity.ok(sentimentStatistics);
    }

    @GetMapping("/dashboard")
    public ResponseEntity<String> getDecisionForDashboard() {
        String decision = "";
        String[] reclamationTypes = {"Technical Issue", "Judging Criteria", "Integrity and Respect", "Feedback"};

        for (String type : reclamationTypes) {
            String recommendation = reclamationCompetitionService.makeDecisionForReclamationType(type);
            if (!recommendation.equals("Aucune recommandation spécifique pour " + type)) {
                decision += recommendation + "\n";
            }
        }

        if (decision.isEmpty()) {
            return ResponseEntity.ok("Aucune recommandation spécifique dans le tableau de bord");
        } else {
            return ResponseEntity.ok(decision);
        }
    }





    @GetMapping("/export/csv")
    public ResponseEntity<byte[]> exportComplaintsToCSV() throws IOException {
        // Récupérer toutes les réclamations
        List<ReclamationCompetition> complaints = reclamationCompetitionService.getAllReclamationCompetition();

        // Configuration de l'en-tête de la réponse HTTP
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("filename", "complaints.csv");

        // Écriture des réclamations dans un fichier CSV
        StringBuilder csvContent = new StringBuilder();
        csvContent.append("ID,Type,Description,Sentiment,Date\n");
        for (ReclamationCompetition complaint : complaints) {
            csvContent.append(String.format("%d,%s,%s,%s,%s\n",
                    complaint.getReclamationId(),
                    complaint.getType(),
                    complaint.getDescription(),
                    complaint.getSentiment(),
                    complaint.getDateCreation()));
        }

        // Convertir le contenu CSV en tableau d'octets
        byte[] csvBytes = csvContent.toString().getBytes();

        // Retourner la réponse avec le fichier CSV
        return new ResponseEntity<>(csvBytes, headers, HttpStatus.OK);
    }






}
