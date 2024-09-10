package com.example.kfanboy.vote.domain.repository;

import static com.example.kfanboy.member.domain.entity.QMember.*;
import static com.example.kfanboy.vote.domain.entity.QVote.*;
import static com.example.kfanboy.vote.domain.entity.QVoteItem.*;
import static com.querydsl.core.types.Projections.*;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.member.dto.UserResponseDto;
import com.example.kfanboy.vote.dto.VoteDetailResponseDto;
import com.example.kfanboy.vote.dto.VoteItemResponseDto;
import com.example.kfanboy.vote.dto.VoteResponseDto;
import com.example.kfanboy.vote.search.VoteSearchCondition;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VoteCustomRepositoryImpl implements VoteCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	/**
	 * 투표 세부정보 + 유저 정보 + 투표 아이템 정보 검색 쿼리
	 * @param voteId 투표 아이디
	 */
	@Override
	public Optional<VoteDetailResponseDto> findByVoteId(final Long voteId) {

		List<VoteDetailResponseDto> list = jpaQueryFactory.selectFrom(vote)
			.innerJoin(voteItem).on(voteItem.voteId.eq(vote.voteId))
			.innerJoin(member).on(vote.memberId.eq(member.memberId))
			.orderBy(voteItem.voteId.desc())
			.where(vote.voteId.eq(voteId))
			.transform(GroupBy.groupBy(vote.voteId).list(
				constructor(VoteDetailResponseDto.class,
					vote.voteId, vote.title, vote.startAt, vote.endAt, vote.isFinished, vote.voteCount,
					vote.maxVoteCount, constructor(UserResponseDto.class, member.memberId, member.email,
						member.nickName, member.userRole), GroupBy.list(constructor(VoteItemResponseDto.class,
						voteItem.voteItemId, voteItem.itemContent, voteItem.itemCount).as("voteItemList")))
			));

		return Optional.ofNullable(list.get(0));
	}

	/**
	 * 투표 리스트
	 */
	@Override
	public PageResponseDto<VoteResponseDto> getVoteList(final VoteSearchCondition voteSearchCondition,
		final Pageable pageable) {

		List<VoteResponseDto> list = jpaQueryFactory.select(
				Projections.constructor(VoteResponseDto.class, vote.voteId, vote.title, vote.startAt, vote.endAt,
					member.memberId, member.nickName))
			.from(vote)
			.innerJoin(member)
			.on(vote.memberId.eq(member.memberId))
			.where(startWithVoteTitle(voteSearchCondition.title()),
				startWithVoteCreatorNickName(voteSearchCondition.nickName()))
			.orderBy(vote.voteId.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		if (list.isEmpty()) {
			return PageResponseDto.toDto(Page.empty());
		}

		JPAQuery<Long> count = jpaQueryFactory.select(vote.count())
			.from(vote)
			.where(startWithVoteTitle(voteSearchCondition.title()),
				startWithVoteCreatorNickName(voteSearchCondition.nickName()));

		return PageResponseDto.toDto(PageableExecutionUtils.getPage(list, pageable, count::fetchOne));
	}

	private BooleanExpression startWithVoteCreatorNickName(final String keyword) {
		return StringUtils.isEmpty(keyword) ? null : member.nickName.startsWith(keyword);
	}

	private BooleanExpression startWithVoteTitle(final String keyword) {
		return StringUtils.isEmpty(keyword) ? null : vote.title.startsWith(keyword);
	}

}
