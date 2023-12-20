package com.example.kfanboy.board.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
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

	public void plusLike() {
		this.likeCount++;
	}

	public void plusComment() {
		this.commentCount++;
	}

	public void plushView() {
		this.viewCount++;
	}

}
