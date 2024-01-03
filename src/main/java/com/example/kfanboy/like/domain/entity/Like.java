package com.example.kfanboy.like.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;

import com.example.kfanboy.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "board_like")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update board_like set deleted_at=current_timestamp where like_id = ?")
public class Like extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "like_id")
	private Long likeId;

	@Column(name = "member_id")
	private Long memberId;

	@Column(name = "board_id")
	private Long boardId;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public Like(Long likeId, Long memberId, Long boardId, LocalDateTime deletedAt) {
		this.likeId = likeId;
		this.memberId = memberId;
		this.boardId = boardId;
		this.deletedAt = deletedAt;
	}
}
