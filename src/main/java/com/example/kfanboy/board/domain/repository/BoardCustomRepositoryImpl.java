package com.example.kfanboy.board.domain.repository;

import static com.example.kfanboy.board.domain.entity.QBoard.*;
import static com.example.kfanboy.category.domain.entity.QCategory.*;
import static com.example.kfanboy.member.domain.entity.QMember.*;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.kfanboy.board.domain.vo.BoardCount;
import com.example.kfanboy.board.dto.BoardResponseDto;
import com.example.kfanboy.board.search.BoardSearchCondition;
import com.example.kfanboy.category.dto.CategoryResponseDto;
import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.member.dto.UserResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class BoardCustomRepositoryImpl implements BoardCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public PageResponseDto<BoardResponseDto> getBoardList(final BoardSearchCondition boardSearchCondition,
		final Pageable pageable) {

		List<BoardResponseDto> list = selectBoardResponseDto().where(startWithTitle(boardSearchCondition.title()),
				startWithWriterNickName(boardSearchCondition.nickName()))
			.orderBy(board.boardId.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		if (list.isEmpty()) {
			return PageResponseDto.toDto(Page.empty());
		}

		JPAQuery<Long> count = jpaQueryFactory.select(board.count())
			.from(board)
			.where(startWithTitle(boardSearchCondition.title()),
				startWithWriterNickName(boardSearchCondition.nickName()));

		return PageResponseDto.toDto(PageableExecutionUtils.getPage(list, pageable, count::fetchOne));
	}

	@Override
	public Optional<BoardResponseDto> findByBoardId(final Long boardId) {
		return Optional.ofNullable(selectBoardResponseDto().where(board.boardId.eq(boardId)).fetchFirst());
	}

	private BooleanExpression startWithWriterNickName(final String keyword) {
		return StringUtils.isEmpty(keyword) ? null : member.nickName.startsWith(keyword);
	}

	private BooleanExpression startWithTitle(final String keyword) {
		return StringUtils.isEmpty(keyword) ? null : board.title.startsWith(keyword);
	}

	private JPAQuery<BoardResponseDto> selectBoardResponseDto() {
		return jpaQueryFactory.select(
				Projections.constructor(BoardResponseDto.class, board.boardId, board.title, board.content,
					Projections.constructor(BoardCount.class, board.boardCount.likeCount, board.boardCount.commentCount,
						board.boardCount.viewCount),
					Projections.constructor(UserResponseDto.class, member.memberId, member.email, member.nickName,
						member.userRole),
					Projections.constructor(CategoryResponseDto.class, category.categoryId, category.categoryName)))
			.from(board)
			.innerJoin(member).on(board.memberId.eq(member.memberId))
			.innerJoin(category).on(board.categoryId.eq(category.categoryId));
	}
}
