package com.example.kfanboy.board.dto;

import java.time.LocalDateTime;

import com.example.kfanboy.board.domain.entity.BoardDocument;
import com.example.kfanboy.member.domain.entity.UserRole;

import lombok.Builder;

@Builder
public record BoardResponseDto(
	Long boardId, String title, String content, Integer likeCount, Integer commentCount, Integer viewCount,
	Long memberId, String email, String nickName, UserRole userRole, Long categoryId, String categoryName,
	LocalDateTime createdAt, LocalDateTime updatedAt) {
	public static BoardResponseDto toDto(BoardDocument boardDocument) {
		return BoardResponseDto.builder()
			.boardId(boardDocument.getBoardId())
			.title(boardDocument.getTitle())
			.content(boardDocument.getContent())
			.likeCount(boardDocument.getLikeCount())
			.commentCount(boardDocument.getCommentCount())
			.viewCount(boardDocument.getViewCount())
			.memberId(boardDocument.getMemberId())
			.email(boardDocument.getEmail())
			.nickName(boardDocument.getNickName())
			.userRole(boardDocument.getUserRole())
			.categoryId(boardDocument.getCategoryId())
			.categoryName(boardDocument.getCategoryName())
			.createdAt(boardDocument.getCreatedAt())
			.updatedAt(boardDocument.getUpdatedAt())
			.build();
	}
}
