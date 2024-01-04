package com.example.kfanboy.like.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kfanboy.board.domain.entity.Board;
import com.example.kfanboy.board.domain.repository.BoardRepository;
import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.exception.ErrorMessage;
import com.example.kfanboy.like.domain.entity.Like;
import com.example.kfanboy.like.domain.repository.LikeRepository;
import com.example.kfanboy.like.dto.LikeResponseDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LikeService {

	private final LikeRepository likeRepository;
	private final BoardRepository boardRepository;

	@Transactional(readOnly = true)
	public LikeResponseDto getLike(final Long memberId, final Long boardId) {
		Optional<Like> like = likeRepository.findByMemberIdAndBoardId(memberId, boardId);

		boolean isLiked = like.isPresent() && like.get().getDeletedAt() == null;

		return LikeResponseDto.builder().isLiked(isLiked).build();
	}

	@Transactional
	public void updateLike(final Long memberId, final Long boardId) {
		Optional<Like> like = likeRepository.findByMemberIdAndBoardId(memberId, boardId);

		boolean isLiked = false;
		if (like.isEmpty()) {
			// 좋아요 최초 선택
			likeRepository.save(Like.builder().boardId(boardId).memberId(memberId).build());

			isLiked = true;
		} else {
			if (like.get().getDeletedAt() == null) {
				// 좋아요 취소
				likeRepository.delete(like.get());
			} else {
				// 좋아요 다시 선택
				likeRepository.updateLike(like.get().getLikeId());
				isLiked = true;
			}
		}

		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		board.getBoardCount().changeLike(isLiked); // 좋아요 개수 카운트 업데이트
	}
}
