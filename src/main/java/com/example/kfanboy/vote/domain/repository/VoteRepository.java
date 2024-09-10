package com.example.kfanboy.vote.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kfanboy.vote.domain.entity.Vote;

@Repository
public interface VoteRepository extends JpaRepository<Vote, Long>, VoteCustomRepository {
}
