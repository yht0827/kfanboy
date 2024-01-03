package com.example.kfanboy.like.domain.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.kfanboy.like.domain.entity.Like;

import io.lettuce.core.dynamic.annotation.Param;

@Repository
public interface LikeRepository extends CrudRepository<Like, Long>, LikeCustomRepository {
	@Modifying
	@Query("UPDATE Like l SET l.deletedAt=null WHERE l.likeId= :likeId")
	void updateLike(@Param("likeId") final Long likeId);
}
