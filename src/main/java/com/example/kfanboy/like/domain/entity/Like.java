package com.example.kfanboy.like.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.springframework.data.annotation.LastModifiedDate;

import jakarta.persistence.Column;
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
@Table(name = "board_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update board_like set deleted_at=current_timestamp where like_id = ?")
public class Like {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_id")
	private Long likeId;

	@NotNull
	@Column(name = "member_id")
	private Long memberId;

	@NotNull
	@Column(name = "board_id")
	private Long boardId;

	@LastModifiedDate
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public Like(Long likeId, Long memberId, Long boardId, LocalDateTime updatedAt, LocalDateTime deletedAt) {
		this.likeId = likeId;
		this.memberId = memberId;
		this.boardId = boardId;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}
}
