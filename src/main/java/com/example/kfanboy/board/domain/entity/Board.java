package com.example.kfanboy.board.domain.entity;

import com.example.kfanboy.board.domain.vo.BoardCount;
import com.example.kfanboy.board.dto.BoardUpdateRequestDto;
import com.example.kfanboy.category.domain.entity.Category;
import com.example.kfanboy.global.common.BaseTimeEntity;
import com.example.kfanboy.member.domain.entity.Member;

import jakarta.persistence.Column;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	@Column(columnDefinition = "TEXT", nullable = false)
	private String content;

	@Embedded
	private BoardCount boardCount;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member member;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Category category;

	@Builder
	public Board(Long boardId, String title, String content, Member member, Category category) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.boardCount = new BoardCount();
		this.member = member;
		this.category = category;
	}

	public Board updateBoard(BoardUpdateRequestDto boardUpdateRequestDto, Category category) {
		this.title = boardUpdateRequestDto.title();
		this.content = boardUpdateRequestDto.content();
		this.category = category;
		return this;
	}
}
