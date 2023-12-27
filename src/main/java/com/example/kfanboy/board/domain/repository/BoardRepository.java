package com.example.kfanboy.board.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kfanboy.board.domain.entity.Board;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long>, BoardCustomRepository {
}
