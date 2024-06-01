package com.artofcode.artofcodebck.Services.Competition;

import com.artofcode.artofcodebck.Entities.Competition;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.Repositories.CompetitionRepository;
import com.artofcode.artofcodebck.user.UserRepository;
import com.artofcode.artofcodebck.Services.CompetitionCandidacy.CompetitionCandidacyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@EnableScheduling
@Service
public class CompetitionServiceImpl implements ICompetitionService {

    @Autowired
    private CompetitionRepository competitionRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Competition getCompetitionById(Long id) {
        return competitionRepository.findById(id).orElse(null);
    }

    @Override
    public List<Competition> getAllCompetitions() {
        return  competitionRepository.findAll();
    }
    public List<Competition> getAllActiveCompetitions() {
        List<Competition> competitions = getAllCompetitions();
        List<Competition> activeCompetitions = new ArrayList<>();

        for (Competition c : competitions) {
            if (!c.isExpired()) {
                activeCompetitions.add(c);
            }
        }

        return activeCompetitions;
    }

    @Override
    public Competition addCompetition(Competition competition) {

        return competitionRepository.saveAndFlush(competition);
    }
    private static final Logger logger = LoggerFactory.getLogger(CompetitionCandidacyServiceImpl.class);

    @Scheduled(fixedRate = 60000)
    public void ValidateCompetitionExpires() {
        logger.info("Début de la vérification et suppression des competitions expirés...");
        LocalDate currentDate = LocalDate.now();
        LocalTime currentTime = LocalTime.now();

        List<Competition> competitions = getAllCompetitions();
        logger.info("Nombre d'Competition récupérés: {}", competitions.size()); // Log pour afficher le nombre  récupérés
        for (Competition c : competitions) {
            logger.info("Competition récupéré: {}", c);
            LocalDate competitionDate = c.getDeadline().toLocalDate();
            LocalTime competitionTime = c.getHour();

            // Comparaison en ignorant les secondes
            if (competitionDate.isEqual(currentDate) &&
                    competitionTime.getHour() == currentTime.getHour() &&
                    competitionTime.getMinute() == currentTime.getMinute()) {
                c.setExpired(true);
                updateeompetition(c, c.getIdCompetition());
                logger.info("Competition expiré : {}", c.getIdCompetition());
            }
        }
        logger.info("Fin de la vérification et suppression des competitions expirés.");
    }
    @Override
    public Competition updateCompetition(Competition competition, Long id) {
        Competition ee= competitionRepository.findById(id).get();
        ee.setCompetitionName(competition.getCompetitionName());
        ee.setDescription(competition.getDescription());
        ee.setHour(competition.getHour());

        ee.setDeadline(competition.getDeadline());
        ee.setCategoryId(competition.getCategoryId());
         return competitionRepository.saveAndFlush(ee);
    }


    public Competition updateeompetition(Competition competition, Long id) {
        Optional<Competition> optionalCompetition = competitionRepository.findById(id);
        if (optionalCompetition.isPresent()) {
            Competition existingCompetition = optionalCompetition.get();
            existingCompetition.setExpired(true); // Marquer la compétition comme expirée
            return competitionRepository.save(existingCompetition);
        } else {
            // Gérer le cas où la compétition n'est pas trouvée
            // Vous pouvez choisir de lever une exception ou de renvoyer null, selon votre logique métier
            return null;
        }
    }

    @Override
    public void deleteCompetition(Long id) {
        if (getCompetitionById(id)!=null)
        {
            competitionRepository.deleteById(id);
        }
    }
    public Optional<User> getuserbyid(Integer id){
        return userRepository.findById(id) ;
    }
    public Page<Competition> gets( int page, int pageSize)  {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return competitionRepository.findAll(pageable);
    }
}
