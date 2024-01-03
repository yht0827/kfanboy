package com.example.kfanboy.member.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.exception.ErrorMessage;
import com.example.kfanboy.global.util.PasswordEncryptor;
import com.example.kfanboy.global.util.SessionKey;
import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.domain.entity.UserRole;
import com.example.kfanboy.member.domain.repository.MemberRepository;
import com.example.kfanboy.member.dto.LoginDto;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginService {

	private final MemberRepository memberRepository;
	private final PasswordEncryptor passwordEncryptor;
	private final HttpSession httpSession;

	/**
	 * 로그인 처리
	 */
	@Transactional
	public void login(final LoginDto loginDto) {
		Member loginUser = findMemberByEmail(loginDto.email());
		passwordEncryptor.validatePassword(loginDto.password(), loginUser.getPassword());

		setUserSession(loginUser);
		loginUser.updateLastLogin();
	}

	/**
	 * 이메일 검증
	 */
	private Member findMemberByEmail(final String email) {
		return memberRepository.findByEmail(email)
			.orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
	}

	/**
	 * 메모리에 세션 저장
	 */
	private void setUserSession(final Member loginUser) {
		httpSession.setAttribute(SessionKey.USER_ID, loginUser.getMemberId());
		httpSession.setAttribute(SessionKey.USER_ROLE, loginUser.getUserRole());
	}

	/**
	 * 로그 아웃(세션 invalidate)
	 */
	public void logout() {
		httpSession.invalidate();
	}

	public Optional<Long> getCurrentUser() {
		return Optional.ofNullable((Long)httpSession.getAttribute(SessionKey.USER_ID));
	}

	public Optional<UserRole> getCurrentRole() {
		return Optional.ofNullable((UserRole)httpSession.getAttribute(SessionKey.USER_ROLE));
	}
}
