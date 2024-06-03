package com.artofcode.artofcodebck.Controllers;

import com.artofcode.artofcodebck.Services.Comment.ICommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@CrossOrigin("http://localhost:4200/api")
@RequestMapping("/comments")
public class CommentRestController {
    private final ICommentService commentService;

}
