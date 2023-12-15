package com.example.kfanboy.member.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.kfanboy.member.domain.entity.Member;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
}
