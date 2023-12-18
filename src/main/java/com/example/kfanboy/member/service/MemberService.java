package com.example.kfanboy.member.service;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.exception.ErrorMessage;
import com.example.kfanboy.global.util.PasswordEncryptor;
import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.domain.repository.MemberRepository;
import com.example.kfanboy.member.dto.JoinDto;
import com.example.kfanboy.member.dto.UserDeleteRequestDto;
import com.example.kfanboy.member.dto.UserResponseDto;
import com.example.kfanboy.member.dto.UserUpdateRequestDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncryptor passwordEncryptor;

	@Transactional(readOnly = true)
	public void isExistsEmail(final String email) {
		Optional.of(!StringUtils.isEmpty(email))
			.filter(Boolean::booleanValue)
			.orElseThrow(() -> new CustomException(ErrorMessage.INPUT_ERROR));

		// 해당 이메일이 존재하면 Exception 발생
		memberRepository.findByEmail(email).ifPresent(e -> {
			throw new CustomException(ErrorMessage.USER_EXISTED);
		});
	}

	@Transactional(readOnly = true)
	public UserResponseDto getProfile(final Long id) {
		return UserResponseDto.toDto(findById(id));
	}

	@Transactional
	public void join(final JoinDto joinDto) {
		isExistsEmail(joinDto.email());
		isEqualPassword(joinDto.password(), joinDto.password2());

		// 비밀번호 암호화 후 저장
		Member member = memberRepository.save(joinDto.toEntity(passwordEncryptor.encrypt(joinDto.password())));

		Optional.of(member)
			.filter(m -> m.getId() > 0)
			.orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_CREATED));
	}

	@Transactional
	public UserResponseDto update(final Long id, final UserUpdateRequestDto userUpdateRequestDto) {
		isEqualPassword(userUpdateRequestDto.password(), userUpdateRequestDto.password2());

		Member oldMember = findById(id);
		return UserResponseDto.toDto(
			oldMember.updateMember(userUpdateRequestDto, passwordEncryptor.encrypt(userUpdateRequestDto.password())));
	}

	@Transactional
	public void delete(final Long id, final UserDeleteRequestDto userDeleteRequestDto) {
		Member member = findById(id);
		passwordEncryptor.validatePassword(userDeleteRequestDto.password(), member.getPassword());
		member.deleteUser();
	}

	private Member findById(final Long id) {
		return memberRepository.findById(id)
			.orElseThrow(() -> new CustomException(ErrorMessage.USER_NOT_FOUND));
	}

	/**
	 * 패스워드 동일한지 확인
	 */
	private void isEqualPassword(final String password1, final String password2) {
		Optional.of(StringUtils.equals(password1, password2))
			.filter(Boolean::booleanValue)
			.orElseThrow(() -> new CustomException(ErrorMessage.INCORRECT_PASSWORD));
	}
}
