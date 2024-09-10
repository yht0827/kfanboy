package com.example.kfanboy.vote.domain.repository;

import static com.example.kfanboy.vote.domain.entity.QVoteItem.*;
import static com.example.kfanboy.vote.domain.entity.QVoteUser.*;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.example.kfanboy.vote.domain.entity.VoteUser;
import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class VoteUserCustomRepositoryImpl implements VoteUserCustomRepository {

	private final JPAQueryFactory jpaQueryFactory;

	@Override
	public Optional<VoteUser> findVoteUser(final Long memberId, final Long voteId) {

		return Optional.ofNullable(jpaQueryFactory.selectFrom(voteUser)
			.innerJoin(voteItem).on(voteUser.voteItemId.eq(voteItem.voteItemId))
			.where(voteUser.memberId.eq(memberId), voteItem.voteId.eq(voteId))
			.fetchFirst());
	}
}
