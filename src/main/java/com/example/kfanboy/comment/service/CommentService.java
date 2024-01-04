package com.example.kfanboy.comment.service;

import org.springframework.stereotype.Service;

import com.example.kfanboy.comment.domain.repository.CommentRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CommentService {

	private final CommentRepository commentRepository;

}
