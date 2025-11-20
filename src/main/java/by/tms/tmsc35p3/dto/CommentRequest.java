package by.tms.tmsc35p3.dto;

import lombok.Builder;

@Builder
public record CommentRequest(
        Long postId,
        Long userId,
        String text
) {
}
