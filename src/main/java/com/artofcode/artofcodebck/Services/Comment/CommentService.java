package com.artofcode.artofcodebck.Services.Comment;



import com.artofcode.artofcodebck.Entities.Comment;
import com.artofcode.artofcodebck.Repositories.ICommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService implements ICommentService {
    private final ICommentRepository commentRepository;

}
