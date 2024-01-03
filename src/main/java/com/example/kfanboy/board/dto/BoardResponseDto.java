package com.example.kfanboy.board.dto;

import com.example.kfanboy.board.domain.entity.Board;
import com.example.kfanboy.board.domain.vo.BoardCount;
import com.example.kfanboy.category.domain.entity.Category;
import com.example.kfanboy.category.dto.CategoryResponseDto;
import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.dto.UserResponseDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BoardResponseDto(
	@NotNull Long boardId, @NotBlank String title, @NotBlank String content,
	BoardCount boardCount, UserResponseDto member, CategoryResponseDto category) {
	public static BoardResponseDto toDto(Board board, Member member, Category category) {
		return BoardResponseDto.builder()
			.boardId(board.getBoardId())
			.title(board.getTitle())
			.content(board.getContent())
			.boardCount(board.getBoardCount())
			.member(UserResponseDto.toDto(member))
			.category(CategoryResponseDto.toDto(category))
			.build();
	}
}
