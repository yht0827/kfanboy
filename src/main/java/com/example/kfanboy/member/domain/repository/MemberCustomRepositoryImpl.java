package com.example.kfanboy.member.domain.repository;

import static com.example.kfanboy.member.domain.entity.QMember.*;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.member.dto.UserResponseDto;
import com.example.kfanboy.member.search.MemberSearchCondition;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

	private final JPAQueryFactory queryFactory;

	@SuppressWarnings("checkstyle:WhitespaceAround")
	@Override
	public PageResponseDto<UserResponseDto> getUserList(final MemberSearchCondition memberSearchCondition,
		final Pageable pageable) {

		List<UserResponseDto> list = queryFactory.select(Projections.constructor(UserResponseDto.class,
				member.memberId,
				member.email,
				member.nickName,
				member.userRole
			))
			.from(member)
			.where(
				likeNickName(memberSearchCondition.nickName()))
			.orderBy(member.memberId.desc())
			.offset(pageable.getOffset())
			.limit(pageable.getPageSize())
			.fetch();

		if (list.isEmpty()) {
			return PageResponseDto.toDto(Page.empty());
		}

		JPAQuery<Long> count = queryFactory
			.select(member.count())
			.from(member)
			.where(
				likeNickName(memberSearchCondition.nickName()));

		return PageResponseDto.toDto(PageableExecutionUtils.getPage(list, pageable, count::fetchOne));
	}

	private BooleanExpression likeNickName(final String keyword) {
		return StringUtils.isEmpty(keyword) ? null : member.nickName.like(keyword);
	}
}
