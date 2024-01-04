package com.example.kfanboy.comment.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kfanboy.like.domain.entity.Like;

@Repository
public interface CommentRepository extends JpaRepository<Like, Long> {
}
