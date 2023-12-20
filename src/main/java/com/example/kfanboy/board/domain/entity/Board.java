package com.example.kfanboy.board.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import com.example.kfanboy.board.domain.vo.BoardCount;
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
public class Board extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "board_id")
	private Long boardId;

	@NotNull
	@Column(nullable = false)
	private String title;

	@NotNull
	@Length(min = 20, max = 500)
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Embedded
	private BoardCount boardCount;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@NotNull
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted;

	@NotNull
	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@NotNull
	@Column(name = "category_id", nullable = false)
	private Long categoryId;

	@Builder
	public Board(Long boardId, String title, String content, Long memberId, Long categoryId, LocalDateTime deletedAt,
		Boolean isDeleted) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.boardCount = new BoardCount();
		this.memberId = memberId;
		this.deletedAt = deletedAt;
		this.categoryId = categoryId;
		this.isDeleted = isDeleted;
	}
}
