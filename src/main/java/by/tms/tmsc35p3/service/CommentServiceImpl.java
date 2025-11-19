package service;

import entity.Comment;
import entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import models.CommentRequest;
import org.springframework.stereotype.Service;
import repository.CommentRepository;
import repository.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void addComment(CommentRequest request) {
        Comment comment = buildComment(request);
        commentRepository.save(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    private Comment buildComment(CommentRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return Comment.builder()
                .user(user)
                .postId(request.postId())
                .text(request.text())
                .createDt(LocalDateTime.now())
                .build();
    }
}