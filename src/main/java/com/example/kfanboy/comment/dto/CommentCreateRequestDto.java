package com.example.kfanboy.comment.dto;

import com.example.kfanboy.comment.domain.entity.Comment;
import com.example.kfanboy.comment.domain.vo.CommentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record CommentCreateRequestDto(@NotNull Long boardId, @NotBlank String content, Long parentCommentId) {

	public Comment toEntity(CommentStatus commentStatus, Long memberId) {
		return Comment.builder()
			.content(this.content)
			.commentStatus(commentStatus)
			.boardId(this.boardId)
			.isDeleted(false)
			.memberId(memberId)
			.build();
	}
}
