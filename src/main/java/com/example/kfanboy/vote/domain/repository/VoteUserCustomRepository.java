package com.example.kfanboy.vote.domain.repository;

import java.util.Optional;

import com.example.kfanboy.vote.domain.entity.VoteUser;

public interface VoteUserCustomRepository {

	Optional<VoteUser> findVoteUser(final Long memberId, final Long voteId);

}
