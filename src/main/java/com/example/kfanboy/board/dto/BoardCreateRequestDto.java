package com.example.kfanboy.board.dto;

import org.hibernate.validator.constraints.Length;

import com.example.kfanboy.board.domain.entity.Board;
import com.example.kfanboy.category.domain.entity.Category;
import com.example.kfanboy.member.domain.entity.Member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record BoardCreateRequestDto(
	@NotBlank @Length(min = 1, max = 100) String title, @NotBlank String content, @NotNull Long categoryId) {

	public Board toEntity(Member member, Category category) {
		return Board.builder()
			.title(this.title)
			.content(this.content)
			.category(category)
			.member(member)
			.build();
	}

}
