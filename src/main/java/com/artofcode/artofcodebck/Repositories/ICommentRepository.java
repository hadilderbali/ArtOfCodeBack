package com.artofcode.artofcodebck.Repositories;


import com.artofcode.artofcodebck.Entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface ICommentRepository extends JpaRepository<Comment,Long> {
    List<Comment> findByTutorialTutorialId(Long tutorialId);


}

