package com.example.kfanboy.comment.domain.repository;

import java.util.Optional;

import org.springframework.data.domain.Pageable;

import com.example.kfanboy.comment.domain.entity.Comment;
import com.example.kfanboy.comment.dto.CommentResponseDto;
import com.example.kfanboy.global.common.response.PageResponseDto;

public interface CommentCustomRepository {

	PageResponseDto<CommentResponseDto> findComments(final Long boardId, final Pageable pageable);

	Long findByBoardIdWithMaxCommentGroup(final Long boardId);

	Optional<Comment> getAvailableSequence(final Long parentId);

	void increaseSequence(final Long groupId, final Long groupOrder);

	void decreaseSequence(final Long groupId, final Long groupOrder);
}
