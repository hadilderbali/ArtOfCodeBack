package com.artofcode.artofcodebck.Repositories;

import com.artofcode.artofcodebck.Entities.BadWord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BadWordRepository extends JpaRepository<BadWord,Long> {
    // Sélectionne tous les mots interdits sous forme de liste de chaînes
    @Query(value = "SELECT word FROM BadWord", nativeQuery = true)
    List<String> findAllWords();

    // Recherche un mot interdit par son libellé (word)
    Optional<BadWord> findByWord(String word);


// Pas besoin de redéfinir la méthode findAll(), elle est héritée de JpaRepository
}