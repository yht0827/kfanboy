package com.example.kfanboy.vote.search;

import lombok.Builder;

@Builder
public record VoteSearchCondition(String title, String nickName) {
}
