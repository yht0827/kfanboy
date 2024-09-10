package com.example.kfanboy.vote.domain.entity;

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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "vote_item")
public class VoteItem {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "vote_item_id")
	private Long voteItemId;

	@Column(name = "item_content", nullable = false)
	private String itemContent;

	@Column(name = "item_count", nullable = false)
	private Long itemCount;

	@Column(name = "vote_id", nullable = false)
	private Long voteId;

	@Builder
	public VoteItem(Long voteItemId, String itemContent, Long itemCount, Long voteId) {
		this.voteItemId = voteItemId;
		this.itemContent = itemContent;
		this.itemCount = itemCount;
		this.voteId = voteId;
	}

	public void updateItemCount() {
		this.itemCount++;
	}
}
