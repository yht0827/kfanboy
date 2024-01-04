package com.example.kfanboy.comment.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor
public class CommentStatus {

	@Column(name = "commentGroup", nullable = false)
	private int commentGroup;

	@Column(name = "comment_group_order", nullable = false)
	private int commentGroupOrder;

	@Column(name = "comment_level", nullable = false)
	private int commentLevel;

	@Column(name = "child_count", nullable = false)
	private int childCount;

	@Builder
	public CommentStatus(int commentGroup, int commentGroupOrder, int commentLevel, int childCount) {
		this.commentGroup = commentGroup;
		this.commentGroupOrder = commentGroupOrder;
		this.commentLevel = commentLevel;
		this.childCount = childCount;
	}
}

