package by.tms.tmsc35p3.controller;

import by.tms.tmsc35p3.service.CommentService;
import by.tms.tmsc35p3.dto.CommentRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/add")
    public ResponseEntity<String> addComment(@RequestBody CommentRequest request) {
        commentService.addComment(request);
        return ResponseEntity.ok("Пользователь успешно добавил комментарий");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id) {
        commentService.deleteComment(id);
        return ResponseEntity.ok("Пользователь успешно удалил комментарий");
    }
}