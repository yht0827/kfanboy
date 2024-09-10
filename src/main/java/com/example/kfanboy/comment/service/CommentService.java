package com.example.kfanboy.comment.service;

import java.util.Objects;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kfanboy.board.domain.entity.Board;
import com.example.kfanboy.board.domain.repository.BoardRepository;
import com.example.kfanboy.comment.domain.entity.Comment;
import com.example.kfanboy.comment.domain.repository.CommentRepository;
import com.example.kfanboy.comment.domain.vo.CommentStatus;
import com.example.kfanboy.comment.dto.CommentCreateRequestDto;
import com.example.kfanboy.comment.dto.CommentDeleteRequestDto;
import com.example.kfanboy.comment.dto.CommentResponseDto;
import com.example.kfanboy.comment.dto.CommentUpdateRequestDto;
import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.exception.ErrorMessage;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;
	private final BoardRepository boardRepository;

	@Transactional(readOnly = true)
	public PageResponseDto<CommentResponseDto> getList(final Long boardId, final Pageable pageable) {
		return commentRepository.findComments(boardId, pageable);
	}

	@Transactional
	public Long create(final Long memberId, final CommentCreateRequestDto commentCreateRequestDto) {
		Board board = boardRepository.findById(commentCreateRequestDto.boardId())
			.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

		Comment comment = commentRepository.save(
			commentCreateRequestDto.toEntity(updateCommentStatus(commentCreateRequestDto, board), memberId));

		// 게시글 댓글 수 증가
		board.getBoardCount().changeComment(true);

		return comment.getCommentId();
	}

	@Transactional
	public void update(final Long memberId, final CommentUpdateRequestDto commentUpdateRequestDto) {
		Comment comment = commentRepository.findById(commentUpdateRequestDto.commentId())
			.orElseThrow(() -> new CustomException(ErrorMessage.COMMENT_NOT_FOUND));

		if (!Objects.equals(comment.getMemberId(), memberId)) {
			throw new CustomException(ErrorMessage.COMMENT_WRITER_NOT_MATCHED);
		}

		comment.updateContent(commentUpdateRequestDto.content());
	}

	@Transactional
	public void delete(final Long memberId, final CommentDeleteRequestDto commentDeleteRequestDto) {
		Comment comment = commentRepository.findById(commentDeleteRequestDto.commentId())
			.orElseThrow(() -> new CustomException(ErrorMessage.COMMENT_NOT_FOUND));

		if (!Objects.equals(comment.getMemberId(), memberId)) {
			throw new CustomException(ErrorMessage.COMMENT_WRITER_NOT_MATCHED);
		}

		if (comment.getCommentStatus().getChildCount() > 0) {
			comment.updateViewDelete();
		} else {
			Board board = boardRepository.findById(comment.getBoardId())
				.orElseThrow(() -> new CustomException(ErrorMessage.BOARD_NOT_FOUND));

			// 정렬 순서 업데이트
			commentRepository.decreaseSequence(comment.getCommentStatus().getCommentGroup(),
				comment.getCommentStatus().getCommentGroupOrder());

			Comment parent = commentRepository.findById(comment.getCommentStatus().getParentId())
				.orElseThrow(() -> new CustomException(ErrorMessage.COMMENT_NOT_FOUND));

			// 부모 카운트 & 게시글 댓글 수 카운트 감소
			parent.getCommentStatus().updateChildCount(false);
			board.getBoardCount().changeComment(false);

			commentRepository.delete(comment);
		}
	}

	/**
	 * 댓글 및 대댓글 저장 로직
	 *  1. 댓글 저장 -> 마지막 그룹 번호 조회(NULL 0으로 변환) + 1을 한다음 저장
	 *  2. 대댓글 저장
	 */
	@Transactional
	public CommentStatus updateCommentStatus(CommentCreateRequestDto commentCreateRequestDto, Board board) {
		// 부모가 존재 하지 않는 경우(댓글 저장)
		if (commentCreateRequestDto.parentCommentId() == 0L) {
			return CommentStatus.builder()
				.commentGroup(commentRepository.findByBoardIdWithMaxCommentGroup(board.getBoardId()) + 1L)
				.commentLevel(0L)
				.parentId(0L)
				.childCount(0L)
				.commentGroupOrder(0L)
				.build();
		} else {
			// 대댓글 저장
			Comment parentComment = commentRepository.findById(commentCreateRequestDto.parentCommentId())
				.orElseThrow(() -> new CustomException(ErrorMessage.COMMENT_NOT_FOUND));

			Optional<Comment> availableSequence = commentRepository.getAvailableSequence(parentComment.getCommentId());

			Long groupOrder;
			Long childCount;

			if (availableSequence.isPresent()) {
				groupOrder = availableSequence.get().getCommentStatus().getCommentGroupOrder();
				childCount = availableSequence.get().getCommentStatus().getChildCount();
			} else {
				groupOrder = parentComment.getCommentStatus().getCommentGroupOrder();
				childCount = 0L;
			}

			// 댓글 순서 업데이트
			Long childOrder = groupOrder + childCount + 1L;
			commentRepository.increaseSequence(parentComment.getCommentStatus().getCommentGroup(), childOrder);

			// 부모 count 업데이트
			parentComment.getCommentStatus().updateChildCount(true);

			return CommentStatus.builder()
				.commentGroup(parentComment.getCommentStatus().getCommentGroup())
				.commentLevel(parentComment.getCommentStatus().getCommentLevel() + 1L)
				.parentId(parentComment.getCommentId())
				.commentGroupOrder(childOrder)
				.childCount(0L)
				.build();
		}
	}
}
