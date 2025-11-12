package service;

import models.CommentRequest;

public interface CommentService {

    void addComment(CommentRequest request);

    void deleteComment(Long id);
}
