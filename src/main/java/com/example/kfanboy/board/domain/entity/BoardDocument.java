package com.example.kfanboy.board.domain.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import com.example.kfanboy.board.dto.BoardUpdateRequestDto;
import com.example.kfanboy.category.domain.entity.Category;
import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.domain.entity.UserRole;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Document(indexName = "board")
@Setting(settingPath = "static/elastic-setting.json")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardDocument {

	@Id
	@Field(type = FieldType.Long)
	private Long boardId;

	@Field(type = FieldType.Text, analyzer = "my_analyzer")
	private String title;

	@Field(type = FieldType.Text)
	private String content;

	@Field(type = FieldType.Integer)
	private Integer likeCount;

	@Field(type = FieldType.Integer)
	private Integer commentCount;

	@Field(type = FieldType.Integer)
	private Integer viewCount;

	@Field(type = FieldType.Long)
	private Long memberId;

	@Field(type = FieldType.Text)
	private String email;

	@Field(type = FieldType.Keyword)
	private String nickName;

	@Field(type = FieldType.Keyword)
	private UserRole userRole;

	@Field(type = FieldType.Long)
	private Long categoryId;

	@Field(type = FieldType.Keyword)
	private String categoryName;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime createdAt;

	@Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
	private LocalDateTime updatedAt;

	@Builder
	public BoardDocument(Long boardId, String title, String content, Integer likeCount, Integer commentCount,
		Integer viewCount, Long memberId, String email, String nickName, UserRole userRole, Long categoryId,
		String categoryName, LocalDateTime createdAt, LocalDateTime updatedAt) {
		this.boardId = boardId;
		this.title = title;
		this.content = content;
		this.likeCount = likeCount;
		this.commentCount = commentCount;
		this.viewCount = viewCount;
		this.memberId = memberId;
		this.email = email;
		this.nickName = nickName;
		this.userRole = userRole;
		this.categoryId = categoryId;
		this.categoryName = categoryName;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
	}

	public static BoardDocument toDoucment(Board board, Member member, Category category) {
		return BoardDocument.builder()
			.boardId(board.getBoardId())
			.title(board.getTitle())
			.content(board.getContent())
			.likeCount(board.getBoardCount().getLikeCount())
			.commentCount(board.getBoardCount().getCommentCount())
			.viewCount(board.getBoardCount().getViewCount())
			.memberId(member.getMemberId())
			.email(member.getEmail())
			.nickName(member.getNickName())
			.userRole(member.getUserRole())
			.categoryId(category.getCategoryId())
			.categoryName(category.getCategoryName())
			.createdAt(board.getCreatedAt())
			.updatedAt(board.getUpdatedAt())
			.build();
	}

	public void updateBoard(BoardUpdateRequestDto boardUpdateRequestDto) {
		this.title = boardUpdateRequestDto.title();
		this.content = boardUpdateRequestDto.content();
		this.categoryId = boardUpdateRequestDto.categoryId();
	}

	public void updateCommentCount(boolean addComment) {
		this.commentCount += addComment ? 1 : -1;
	}

	public void updateLikeCount(boolean addLike) {
		this.likeCount += addLike ? 1 : -1;
	}

}
