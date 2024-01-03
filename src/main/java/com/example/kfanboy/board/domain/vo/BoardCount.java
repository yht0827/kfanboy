package com.example.kfanboy.board.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class BoardCount {
	@NotNull
	@Column(name = "like_count", nullable = false)
	private int likeCount;

	@NotNull
	@Column(name = "comment_count", nullable = false)
	private int commentCount;

	@NotNull
	@Column(name = "view_count", nullable = false)
	private int viewCount;

	@Builder
	public BoardCount(int likeCount, int commentCount, int viewCount) {
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.viewCount = viewCount;
	}

	public void changeLike(boolean addLike) {
		this.likeCount += addLike ? 1 : -1;
	}

	public void changeComment(boolean addComment) {
		this.commentCount += addComment ? 1 : -1;
	}

	public void changeView(boolean addView) {
		this.viewCount += addView ? 1 : -1;
	}
}
