package com.example.kfanboy.global.member;

import static com.example.kfanboy.global.member.MemberDtoTest.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.util.PasswordEncryptor;
import com.example.kfanboy.member.domain.entity.Member;
import com.example.kfanboy.member.domain.repository.MemberRepository;
import com.example.kfanboy.member.dto.LoginDto;
import com.example.kfanboy.member.service.LoginService;

import jakarta.servlet.http.HttpSession;

@ExtendWith(MockitoExtension.class)
public class LoginServiceTest {
	@Mock
	MemberRepository memberRepository;
	@Mock
	PasswordEncryptor passwordEncryptor;
	@Mock
	HttpSession httpSession;

	@InjectMocks
	LoginService loginService;

	@Test
	@DisplayName("로그인을 성공한다.")
	public void login_success() {

		// given
		LoginDto loginDto = getLoginDto();
		Member member = getMember();

		given(memberRepository.findByEmail(any())).willReturn(Optional.of(member));

		// when
		loginService.login(loginDto);

		// then
		assertThat(member.getLastLoginAt().withNano(0))
			.isEqualTo(LocalDateTime.now().withNano(0));

		verify(memberRepository, atLeastOnce()).findByEmail(any());
	}

	@Test
	@DisplayName("로그인 실패한다.")
	public void login_fail() {
		// given
		LoginDto loginDto = getLoginDto();

		// when
		when(memberRepository.findByEmail(any())).thenThrow(CustomException.class);

		// then
		assertThrows(CustomException.class, () -> loginService.login(loginDto));

		verify(memberRepository, atLeastOnce()).findByEmail(any());
	}

}
