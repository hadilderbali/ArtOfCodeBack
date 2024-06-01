package com.artofcode.artofcodebck.Services.CompetitionCandidacy;


import com.artofcode.artofcodebck.Entities.Competition;
import com.artofcode.artofcodebck.Entities.CompetitionCandidacy;
import com.artofcode.artofcodebck.user.User;
import com.artofcode.artofcodebck.Repositories.CompetitionCandidacyRepository;
import com.artofcode.artofcodebck.Repositories.CompetitionRepository;
import com.artofcode.artofcodebck.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;


@Service
public class CompetitionCandidacyServiceImpl implements ICompetitionCandidacyService {

    @Autowired
    private CompetitionCandidacyRepository competitionCandidacyRepository;
@Autowired
private UserRepository userRepository;
@Autowired
private CompetitionRepository competitionRepository;
    @Override
    public CompetitionCandidacy getCompetitionCandidacyById(Long id) {
        return competitionCandidacyRepository.findById(id).orElse(null);
    }

    public List<CompetitionCandidacy> getCompetitionCandidacyByIduser(int iduser) {
        User user = userRepository.findById(iduser).get();
        System.out.println(user.getCompetitionCandidacy());
        List<CompetitionCandidacy> com =  competitionCandidacyRepository.findAll();
        List<CompetitionCandidacy> competitionss = new ArrayList<CompetitionCandidacy>();
        for(CompetitionCandidacy c : com){
            if(c.getUser().equals(user)){
                competitionss.add(c);
            }
        }
        return competitionss;
    }

    @Override
    public List<CompetitionCandidacy> getAllCompetitionCandidacies() {
        return  competitionCandidacyRepository.findByCompetitionExpiredTrue();
    }

    @Override
    public CompetitionCandidacy addCompetitionCandidacy(CompetitionCandidacy competitionCandidacy, Long idcomptetion, Long userId) {
        return null;
    }

    public CompetitionCandidacy addCompetitionCandidacy(CompetitionCandidacy competitionCandidacy,Long idcomptetion,int userId) {
        User user = userRepository.findById(userId).get();
        Competition competition = competitionRepository.findById(idcomptetion).get();
        competitionCandidacy.setUsername(user.getUsername());
        competitionCandidacy.setUserlastname(user.getLastname());
        competitionCandidacy.setCompetitionTitle(competition.getCompetitionName());
        competitionCandidacy.setCompetition(competition);
        competitionCandidacy.setUser(user);
        return competitionCandidacyRepository.save(competitionCandidacy);
    }
    @Override
    public CompetitionCandidacy updateCompetitionCandidacy(CompetitionCandidacy competitionCandidacy,Long id) {
        CompetitionCandidacy cc = competitionCandidacyRepository.findById(id).orElse(null);
        if (cc != null && competitionCandidacy.getVideo() != null) {
            cc.setVideo(competitionCandidacy.getVideo().substring(12));
            return competitionCandidacyRepository.saveAndFlush(cc);
        } else {
            // Gérer le cas où la candidature ou la vidéo est null
            return null;
        }
    }



    @Override
    public void deleteCompetitionCandidacy(Long id) {
        if (getCompetitionCandidacyById(id)!=null)
        {
            competitionCandidacyRepository.deleteById(id);
        }
    }
}
