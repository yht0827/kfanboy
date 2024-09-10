package com.example.kfanboy.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MaxCountEnum {

	// vote
	MAX_VOTE_ITEM(10),

	DUMMY(0);

	private final int maxCount;
}
