package com.example.kfanboy.like.domain.repository;

import static com.example.kfanboy.like.domain.entity.QLike.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.kfanboy.like.domain.entity.Like;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class LikeCustomRepositoryImpl implements LikeCustomRepository {
	private final JPAQueryFactory queryFactory;

	@Override
	public Optional<Like> findByMemberIdAndBoardId(Long memberId, Long boardId) {
		return Optional.ofNullable(queryFactory.selectFrom(like)
			.where(like.memberId.eq(memberId), like.boardId.eq(boardId))
			.fetchOne());
	}
}
