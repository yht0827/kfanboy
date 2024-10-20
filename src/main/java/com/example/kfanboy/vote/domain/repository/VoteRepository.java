package com.example.kfanboy.vote.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.example.kfanboy.vote.domain.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>, VoteCustomRepository {

	/**
	 * 테스트 코드 용 쿼리
	 */
	@Transactional
	@Modifying
	@Query("DELETE FROM Vote v")
	void deleteAllEntities();
}
