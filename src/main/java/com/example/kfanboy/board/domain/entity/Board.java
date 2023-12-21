package com.example.kfanboy.board.domain.entity;

import java.time.LocalDateTime;

import com.example.kfanboy.board.domain.vo.BoardCount;
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

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@NotNull
	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Member member;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
	private Category category;

	@Builder
	public Board(Long boardId, String title, String content, LocalDateTime deletedAt,
		Boolean isDeleted, Member member, Category category) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.boardCount = new BoardCount();
		this.deletedAt = deletedAt;
		this.isDeleted = isDeleted;
		this.member = member;
		this.category = category;
	}
}
