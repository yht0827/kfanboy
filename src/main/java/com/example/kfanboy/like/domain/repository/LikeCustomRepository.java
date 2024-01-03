package com.example.kfanboy.like.domain.repository;

import java.util.Optional;

import com.example.kfanboy.like.domain.entity.Like;

public interface LikeCustomRepository {
	Optional<Like> findByMemberIdAndBoardId(final Long memberId, final Long boardId);
}
