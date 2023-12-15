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

	@Transactional
	public void login(final LoginDto loginDto) {
		Member loginUser = memberRepository.findByEmail(loginDto.email())
			.orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));

		boolean isValidPassword = passwordEncryptor.isMatch(loginDto.password(), loginUser.getPassword());

		if (!isValidPassword) {
			throw new CustomException(ErrorMessage.INCORRECT_PASSWORD_OR_USER_EMAIL);
		}

		httpSession.setAttribute(SessionKey.USER_ID, loginUser.getId());
		httpSession.setAttribute(SessionKey.USER_ROLE, loginUser.getUserRole());

		loginUser.updateLastLogin();
	}

	public Optional<Long> getCurrentUser() {
		return Optional.ofNullable((Long)httpSession.getAttribute(SessionKey.USER_ID));
	}

	public Optional<UserRole> getCurrentRole() {
		return Optional.ofNullable((UserRole)httpSession.getAttribute(SessionKey.USER_ROLE));
	}

	public void logout() {
		httpSession.invalidate();
	}

}
