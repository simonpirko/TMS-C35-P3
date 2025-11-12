package models;

import lombok.Builder;

@Builder
public record CommentRequest(
        Long postId,
        Long userId,
        String text
) {
}
