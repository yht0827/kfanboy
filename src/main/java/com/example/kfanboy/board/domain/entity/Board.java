package com.example.kfanboy.board.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import com.example.kfanboy.board.domain.vo.BoardCount;
import com.example.kfanboy.board.dto.BoardUpdateRequestDto;
import com.example.kfanboy.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "board")
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "update board set deleted_at=current_timestamp where board_id = ?")
public class Board extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long boardId;

	@NotNull
	@Column(nullable = false)
	private String title;

	@NotNull
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Embedded
	private BoardCount boardCount;

	@NotNull
	@Column(name = "member_id")
	private Long memberId;

	@NotNull
	@Column(name = "category_id")
	private Long categoryId;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public Board(Long boardId, String title, String content, Long memberId, Long categoryId,
		LocalDateTime deletedAt) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.boardCount = new BoardCount();
		this.memberId = memberId;
		this.categoryId = categoryId;
		this.deletedAt = deletedAt;
	}

	public Board updateBoard(BoardUpdateRequestDto boardUpdateRequestDto, Long categoryId) {
		this.title = boardUpdateRequestDto.title();
		this.content = boardUpdateRequestDto.content();
		this.categoryId = categoryId;
		return this;
	}
}
