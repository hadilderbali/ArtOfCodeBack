package com.artofcode.artofcodebck.Services.Tutorial;

import com.artofcode.artofcodebck.Entities.*;
import com.artofcode.artofcodebck.Repositories.BadWordRepository;
import com.artofcode.artofcodebck.Repositories.ICommentRepository;
import com.artofcode.artofcodebck.Repositories.ITutorialRepository;
import com.artofcode.artofcodebck.Repositories.IUserRepository;
import com.artofcode.artofcodebck.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;




@Service
@RequiredArgsConstructor
public class TutorialService implements ITutorialService {
    private final ITutorialRepository tutorialRepository;
    private final IUserRepository userRepository; // Injection du repository utilisateur


    @Override
    public Tutorial addOrUpdateTutorial(Tutorial tutorial) {

        return tutorialRepository.save(tutorial);
    }

    @Override
    public Tutorial getTutorialById(long tutorialId) {
        return tutorialRepository.findById(tutorialId).orElse(null);
    }

    @Override
    public void deleteTutorial(long tutorialId) {
        tutorialRepository.deleteById(tutorialId);

    }

    @Override
    public List<Tutorial> getAllTutorials() {
        return tutorialRepository.findAll();
    }

    //filter
    public List<Tutorial> filterCoursesByLevel(Level level) {
        return tutorialRepository.findByLevel(level);
    }


    @Override
    public Tutorial updateTutorial(Tutorial tutorial, Long id) {
        Tutorial existingTutorial = tutorialRepository.findById(id).orElse(null);
        if (existingTutorial != null) {
            existingTutorial.setTitle(tutorial.getTitle());
            existingTutorial.setDescription(tutorial.getDescription());
            existingTutorial.setDuration(tutorial.getDuration());
            existingTutorial.setCategory(tutorial.getCategory());
            String video = tutorial.getVideo();
            if (video.length() > 12) {
                existingTutorial.setVideo(video.substring(12));
            }
            return tutorialRepository.saveAndFlush(existingTutorial);
        }
        return null; // Or handle as needed, like throwing an exception
    }

    public Page<Tutorial> gets(int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 3, pageSize);
        return tutorialRepository.findAll(pageable);
    }


    public void likeTutorial(Long tutorialId) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new NoSuchElementException("Tutorial not found with id " + tutorialId));
        tutorial.setLikes(tutorial.getLikes() + 1);
        tutorialRepository.save(tutorial);
    }

    public void dislikeTutorial(Long tutorialId) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new NoSuchElementException("Tutorial not found with id " + tutorialId));
        tutorial.setDislikes(tutorial.getDislikes() + 1);
        tutorialRepository.save(tutorial);
    }




    //comment
    private final ICommentRepository commentRepository;
    private  final  BadWordRepository badWordRepository;


   /* @Override

  public Comment addComment(Long tutorialId, Comment comment) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutorial not found with id " + tutorialId));
        comment.setTutorial(tutorial);
        // Vous pouvez ajouter des logiques de validation ou de traitement supplémentaires ici
        return commentRepository.save(comment);
    }*/

    @Override
    public boolean addComment(Long tutorialId, Long userId, Comment comment) {
        List<BadWord> badWords = badWordRepository.findAll();

        String commentContent = comment.getContent();

        // Vérifie si le commentaire contient des mots interdits
        if (containsBadWord(commentContent)) {
            // Remplace les mots interdits par des étoiles
            commentContent = replaceBadWords(commentContent);

            // Ne pas ajouter le commentaire s'il ne contient que des mots interdits
            if (commentContent.trim().isEmpty()) {
                return false;
            }

            // Mettre à jour le contenu du commentaire avec les mots filtrés
            comment.setContent(commentContent);
        }

        // Récupérer le tutoriel associé au commentaire
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutorial not found with id " + tutorialId));

        // Récupérer l'utilisateur associé au commentaire
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with id " + userId));

        // Associer le commentaire au tutoriel et à l'utilisateur
        comment.setTutorial(tutorial);
        comment.setUser(user);

        // Enregistrer le commentaire dans la base de données
        commentRepository.save(comment);

        return true;
    }

    // Autres méthodes...

    private boolean containsBadWord(String content) {
        Iterable<BadWord> badWords = badWordRepository.findAll();

        for (BadWord badWord : badWords) {
            // Vérifier si le contenu du commentaire contient le mot interdit (insensible à la casse)
            if (content.toLowerCase().contains(badWord.getWord().toLowerCase())) {
                return true; // Mot interdit trouvé dans le contenu du commentaire
            }
        }
        return false; // Aucun mot interdit trouvé
    }

    private String replaceBadWords(String content) {
        Iterable<BadWord> badWords = badWordRepository.findAll();
        for (BadWord badWord : badWords) {
            String regex = "\\b" + badWord.getWord() + "\\b";
            content = content.replaceAll(regex, "*".repeat(badWord.getWord().length()));
        }
        return content;
    }




    @Override
    public List<Comment> getComments(Long tutorialId) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tutorial not found with id " + tutorialId));
        return tutorial.getComments();
    }


    //filter
    @Override
    public Page<Tutorial> getTutorials(Specification<Tutorial> spec, int page, int pageSize) {
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        return tutorialRepository.findAll(spec, pageable);
    }

    //share
   public String generateSharedLink(Long tutorialId) {
        Tutorial tutorial = tutorialRepository.findById(tutorialId).orElse(null);
        if (tutorial != null) {
            String videoUrl = tutorial.getVideo();
            // Encodez l'URL de la vidéo pour éviter les problèmes avec les caractères spéciaux dans les URL
            String encodedVideoUrl = encodeURL(videoUrl);
            // Construisez le lien de partage avec l'URL encodée
            String sharedLink = "http://example.com/share?url=" + encodedVideoUrl;
            return sharedLink;
        } else {
            return null; // Tutoriel non trouvé, renvoie null
        }
    }

    // Méthode utilitaire pour encoder l'URL
    private String encodeURL(String url) {
        try {
            return URLEncoder.encode(url, StandardCharsets.UTF_8.toString());
        } catch (UnsupportedEncodingException e) {
            // Gestion de l'exception si l'encodage n'est pas pris en charge
            e.printStackTrace();
            return url; // Retourne l'URL non encodée en cas d'erreur
        }
    }

@Override
    public Map<Level, Integer> calculateStatisticsByLevel() {
        Map<Level, Integer> statisticsByLevel = new HashMap<>();

        // Parcourir tous les niveaux
        for (Level level : Level.values()) {
            // Filtrer les tutoriels par niveau
            List<Tutorial> tutorials = tutorialRepository.findByLevel(level);

            // Calculer le nombre de tutoriels pour ce niveau
            int count = tutorials.size();

            // Ajouter les statistiques à la map
            statisticsByLevel.put(level, count);
        }

        return statisticsByLevel;
    }



    }








