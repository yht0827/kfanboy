package com.example.kfanboy.comment.domain.repository;

import static com.example.kfanboy.comment.domain.entity.QComment.*;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.kfanboy.comment.domain.entity.Comment;
import com.example.kfanboy.comment.domain.entity.QComment;
import com.example.kfanboy.comment.dto.CommentResponseDto;
import com.example.kfanboy.global.common.response.PageResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CommentCustomRepositoryImpl implements CommentCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public PageResponseDto<CommentResponseDto> findComments(final Long boardId, final Pageable pageable) {

		List<CommentResponseDto> list = jpaQueryFactory.select(
				Projections.constructor(CommentResponseDto.class,
					comment.commentId, comment.content, comment.isDeleted, comment.commentStatus.commentGroup,
					comment.commentStatus.commentLevel))
			.from(comment)
			.where(comment.boardId.eq(boardId))
			.orderBy(comment.commentStatus.commentGroup.asc())
			.orderBy(comment.commentStatus.commentGroupOrder.asc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		if (list.isEmpty()) {
			return PageResponseDto.toDto(Page.empty());
		}

		JPAQuery<Long> count = jpaQueryFactory.select(comment.count())
			.from(comment)
			.where(comment.boardId.eq(boardId));

		return PageResponseDto.toDto(PageableExecutionUtils.getPage(list, pageable, count::fetchOne));
	}

	@Override
	public Long findByBoardIdWithMaxCommentGroup(Long boardId) {
		return jpaQueryFactory.select(comment.commentStatus.commentGroup.longValue().max().coalesce(0L))
			.from(comment)
			.where(comment.boardId.eq(boardId))
			.fetchOne();
	}

	@Override
	public Optional<Comment> getAvailableSequence(Long parentId) {
		QComment sub = new QComment("sub");

		return Optional.ofNullable(jpaQueryFactory.selectFrom(comment)
			.where(comment.commentStatus.parentId.eq(parentId)
				.and(comment.commentStatus.commentGroupOrder
					.eq(jpaQueryFactory
						.select(sub.commentStatus.commentGroupOrder.max())
						.from(sub)
						.where(sub.commentStatus.parentId.eq(parentId)))))
			.fetchFirst());
	}

	@Override
	public void increaseSequence(Long groupId, Long groupOrder) {
		jpaQueryFactory
			.update(comment)
			.set(comment.commentStatus.commentGroupOrder, comment.commentStatus.commentGroupOrder.add(1L))
			.where(comment.commentStatus.commentGroup.eq(groupId)
				.and(comment.commentStatus.commentGroupOrder.goe(groupOrder)))
			.execute();
	}

	@Override
	public void decreaseSequence(Long groupId, Long groupOrder) {
		jpaQueryFactory
			.update(comment)
			.set(comment.commentStatus.commentGroupOrder, comment.commentStatus.commentGroupOrder.subtract(1L))
			.where(comment.commentStatus.commentGroup.eq(groupId)
				.and(comment.commentStatus.commentGroupOrder.gt(groupOrder)))
			.execute();
	}

}
