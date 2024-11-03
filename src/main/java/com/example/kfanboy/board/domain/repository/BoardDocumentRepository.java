package com.example.kfanboy.board.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.example.kfanboy.board.domain.entity.BoardDocument;

public interface BoardDocumentRepository extends ElasticsearchRepository<BoardDocument, Long> {

	Page<BoardDocument> findAllByTitleContaining(String title, Pageable pageable);

	Page<BoardDocument> findAllByNickName(String nickName, Pageable pageable);
}
