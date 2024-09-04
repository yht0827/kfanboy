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

	/**
	 * 댓글 그룹 번호
	 */
	@Column(name = "comment_group", nullable = false)
	private Long commentGroup;

	/**
	 * 댓글 레벨 (들여 쓰기)
	 */
	@Column(name = "comment_level", nullable = false)
	private Long commentLevel;

	/**
	 * 댓글 그룹 순서
	 */
	@Column(name = "comment_group_order", nullable = false)
	private Long commentGroupOrder;

	/**
	 * 부모 댓글 ID
	 */
	@Column(name = "parent_id", nullable = false)
	private Long parentId;

	/**
	 * 자식 댓글 개수
	 */
	@Column(name = "child_count", nullable = false)
	private Long childCount;

	@Builder
	public CommentStatus(Long commentGroup, Long commentLevel, Long commentGroupOrder, Long parentId, Long childCount) {
		this.commentGroup = commentGroup;
		this.commentLevel = commentLevel;
		this.commentGroupOrder = commentGroupOrder;
		this.parentId = parentId;
		this.childCount = childCount;
	}

	public void updateChildCount(boolean addCount) {
		this.childCount += addCount ? 1 : -1;
	}

}

