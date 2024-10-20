package com.example.kfanboy.vote.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kfanboy.global.common.MaxCountEnum;
import com.example.kfanboy.global.common.annotation.DistributeLock;
import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.exception.ErrorMessage;
import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.domain.repository.MemberRepository;
import com.example.kfanboy.vote.domain.entity.Vote;
import com.example.kfanboy.vote.domain.entity.VoteItem;
import com.example.kfanboy.vote.domain.repository.VoteItemRepository;
import com.example.kfanboy.vote.domain.repository.VoteRepository;
import com.example.kfanboy.vote.domain.repository.VoteUserRepository;
import com.example.kfanboy.vote.dto.VoteActRequestDto;
import com.example.kfanboy.vote.dto.VoteCreateRequestDto;
import com.example.kfanboy.vote.dto.VoteDetailResponseDto;
import com.example.kfanboy.vote.dto.VoteResponseDto;
import com.example.kfanboy.vote.search.VoteSearchCondition;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VoteService {

	private final VoteRepository voteRepository;
	private final VoteUserRepository voteUserRepository;
	private final VoteItemRepository voteItemRepository;
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public VoteDetailResponseDto getDetailVote(final Long voteId) {
		return voteRepository.findByVoteId(voteId).orElseThrow(() -> new CustomException(ErrorMessage.VOTE_NOT_FOUND));
	}

	@Transactional(readOnly = true)
	public PageResponseDto<VoteResponseDto> getVoteList(final VoteSearchCondition voteSearchCondition,
		final Pageable pageable) {
		return voteRepository.getVoteList(voteSearchCondition, pageable);
	}

	@Transactional
	public Long createVote(final Long memberId, final VoteCreateRequestDto voteCreateRequestDto) {
		Member member = memberRepository.findById(memberId)
			.orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

		// 투표 아이템 최대 개수 체크
		if (voteCreateRequestDto.voteItemList().size() > MaxCountEnum.MAX_VOTE_ITEM.getMaxCount()) {
			throw new CustomException(ErrorMessage.VOTE_ITEM_TOO_MANY);
		}

		// 투표 DTO -> Entity 변환
		Vote vote = voteRepository.save(voteCreateRequestDto.toEntity(member));

		// 투표 아이템 리스트 DTO -> Entity 변환
		List<VoteItem> voteItemList = voteCreateRequestDto.voteItemList()
			.stream()
			.map(v -> v.toEntity(vote.getVoteId()))
			.toList();

		voteItemRepository.saveAll(voteItemList);

		return vote.getVoteId();
	}

	/**
	 * 투표 하기 로직
	 */
	@DistributeLock(key = "#key")
	public Long voteAct(final String key, final Long memberId, final VoteActRequestDto voteActRequestDto) {
		Vote vote = voteRepository.findById(voteActRequestDto.voteId())
			.orElseThrow(() -> new CustomException(ErrorMessage.VOTE_NOT_FOUND));

		VoteItem voteItem = voteItemRepository.findById(voteActRequestDto.voteItemId())
			.orElseThrow(() -> new CustomException(ErrorMessage.VOTE_ITEM_NOT_FOUND));

		// 중복 투표 체크
		if (voteUserRepository.findVoteUser(memberId, voteItem.getVoteId()).isPresent()) {
			throw new CustomException(ErrorMessage.VOTE_USER_EXISTED);
		}

		// 선착순 인원 체크
		if (vote.getVoteCount() >= vote.getMaxVoteCount()) {
			throw new CustomException(ErrorMessage.VOTE_COUNT_LIMIT);
		}

		// 투표 종료 여부 체크 및 투표 시작, 종료시간 체크
		if (vote.getIsFinished() || vote.getStartAt().isAfter(LocalDateTime.now()) || vote.getEndAt()
			.isBefore(LocalDateTime.now())) {
			throw new CustomException(ErrorMessage.VOTE_TIME_EXPIRED);
		}

		// 투표 아이템 증가, 투표 개수 증가 처리 로직
		voteItem.updateItemCount();
		vote.updateVoteCount();

		return voteUserRepository.save(voteActRequestDto.toEntity(voteItem.getVoteItemId(), memberId))
			.getVoteUserId();
	}
}
