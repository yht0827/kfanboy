package com.example.kfanboy.board.search;

import lombok.Builder;

@Builder
public record BoardSearchCondition(String title, String nickName) {
}
