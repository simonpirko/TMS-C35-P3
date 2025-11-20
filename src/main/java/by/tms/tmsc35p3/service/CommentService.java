package by.tms.tmsc35p3.service;

import by.tms.tmsc35p3.entity.Account;
import by.tms.tmsc35p3.entity.Comment;
import by.tms.tmsc35p3.repository.AccountRepository;
import by.tms.tmsc35p3.repository.CommentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import by.tms.tmsc35p3.dto.CommentRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void addComment(CommentRequest request) {
        Comment comment = buildComment(request);
        commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }

    private Comment buildComment(CommentRequest request) {
        Account account = accountRepository.findById(request.userId())
                .orElseThrow(() -> new RuntimeException("Пользователь не найден"));

        return Comment.builder()
                .account(account)
                .postId(request.postId())
                .text(request.text())
                .createDt(LocalDateTime.now())
                .build();
    }
}