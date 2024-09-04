package com.example.kfanboy.comment.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import com.example.kfanboy.comment.domain.vo.CommentStatus;
import com.example.kfanboy.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "comment")
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "update comment set deleted_at=current_timestamp where comment_id = ?")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "comment_id")
	private Long commentId;

	@NotBlank
	@Length(min = 1, max = 255)
	@Column(name = "content", nullable = false)
	private String content;

	@Embedded
	private CommentStatus commentStatus;

	@Column(name = "board_id", nullable = false)
	private Long boardId;

	@Column(name = "member_id", nullable = false)
	private Long memberId;

	@Column(name = "is_deleted", nullable = false)
	private Boolean isDeleted;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public Comment(Long commentId, String content, CommentStatus commentStatus, Long boardId, Long memberId,
		Boolean isDeleted, LocalDateTime deletedAt) {
		this.commentId = commentId;
		this.content = content;
		this.commentStatus = commentStatus;
		this.boardId = boardId;
		this.memberId = memberId;
		this.isDeleted = isDeleted;
		this.deletedAt = deletedAt;
	}

	public void updateContent(String content) {
		this.content = content;
	}

	public void updateViewDelete() {
		this.isDeleted = true;
	}

}
