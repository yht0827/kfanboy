package com.example.kfanboy.global.vote;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.kfanboy.vote.domain.entity.Vote;
import com.example.kfanboy.vote.domain.entity.VoteItem;
import com.example.kfanboy.vote.domain.repository.VoteItemRepository;
import com.example.kfanboy.vote.domain.repository.VoteRepository;
import com.example.kfanboy.vote.domain.repository.VoteUserRepository;
import com.example.kfanboy.vote.dto.VoteActRequestDto;
import com.example.kfanboy.vote.service.VoteApplication;

@SpringBootTest
public class VoteConcurrencyTest {

	@Autowired
	private VoteApplication voteApplication;
	@Autowired
	private VoteRepository voteRepository;
	@Autowired
	private VoteItemRepository voteItemRepository;
	@Autowired
	private VoteUserRepository voteUserRepository;

	private static Long VOTE_ID;
	private static Long VOTE_ITEM_ID;

	@BeforeEach
	public void insert() {
		Vote vote = voteRepository.save(
			Vote.builder()
				.voteId(1L)
				.title("vote1")
				.startAt(LocalDateTime.now())
				.endAt(LocalDateTime.of(2025, 1, 1, 12, 0))
				.isFinished(false)
				.voteCount(0L)
				.maxVoteCount(200L)
				.memberId(1L)
				.build()
		);

		VOTE_ID = vote.getVoteId();

		VoteItem voteItem = VoteItem.builder()
			.itemContent("item1")
			.itemCount(0L)
			.voteId(VOTE_ID)
			.build();

		VoteItem item = voteItemRepository.save(voteItem);

		VOTE_ITEM_ID = item.getVoteItemId();

	}

	@AfterEach
	public void delete() {
		voteUserRepository.deleteAll();
		voteItemRepository.deleteAll();
		voteRepository.deleteAllEntities();
	}

	@Test
	public void 투표() {
		VoteActRequestDto voteActRequestDto = VoteActRequestDto.builder()
			.voteId(VOTE_ID)
			.voteItemId(VOTE_ITEM_ID)
			.build();

		voteApplication.voteAct(1L, voteActRequestDto);

		Vote vote = voteRepository.findById(VOTE_ID).orElseThrow();

		assertEquals(vote.getVoteCount(), 1L);
	}

	@Test
	public void 투표_분산락_적용_선착순_200명_테스트() throws InterruptedException {
		int numberOfThreads = 1000;
		ExecutorService executorService = Executors.newFixedThreadPool(numberOfThreads);
		CountDownLatch latch = new CountDownLatch(numberOfThreads);

		VoteActRequestDto voteActRequestDto = VoteActRequestDto.builder()
			.voteId(VOTE_ID)
			.voteItemId(VOTE_ITEM_ID)
			.build();

		for (int i = 0; i < numberOfThreads; i++) {
			Long memberId = i + 1L;
			executorService.submit(() -> {
				try {
					voteApplication.voteAct(memberId, voteActRequestDto);
				} finally {
					latch.countDown();
				}
			});
		}

		latch.await();

		Vote vote = voteRepository.findById(VOTE_ID).orElseThrow();
		long count = voteUserRepository.count();

		assertThat(count).isEqualTo(vote.getMaxVoteCount());
	}
}
