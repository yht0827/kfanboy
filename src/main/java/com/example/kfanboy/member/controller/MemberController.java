package com.example.kfanboy.member.controller;

import static com.example.kfanboy.global.common.response.ResponseHandler.*;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.kfanboy.global.common.SuccessMessage;
import com.example.kfanboy.global.common.annotation.CurrentUser;
import com.example.kfanboy.global.common.annotation.LoginCheck;
import com.example.kfanboy.global.common.response.PageResponseDto;
import com.example.kfanboy.global.common.response.ResponseDto;
import com.example.kfanboy.member.domain.entity.UserRole;
import com.example.kfanboy.member.dto.JoinDto;
import com.example.kfanboy.member.dto.LoginDto;
import com.example.kfanboy.member.dto.UserDeleteRequestDto;
import com.example.kfanboy.member.dto.UserResponseDto;
import com.example.kfanboy.member.dto.UserUpdateRequestDto;
import com.example.kfanboy.member.search.MemberSearchCondition;
import com.example.kfanboy.member.service.LoginService;
import com.example.kfanboy.member.service.MemberService;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/member")
public class MemberController {

	private final LoginService loginService;
	private final MemberService memberService;

	@GetMapping("/list")
	@LoginCheck(role = UserRole.ROLE_ADMIN)
	public ResponseEntity<PageResponseDto<UserResponseDto>> getUserList(
		final MemberSearchCondition memberSearchCondition, final Pageable pageable) {
		return success(memberService.getUserList(memberSearchCondition, pageable));
	}

	@GetMapping("/email-exists")
	public ResponseEntity<ResponseDto<?>> isExistsEmail(@Email @RequestParam("email") final String email) {
		memberService.isExistsEmail(email);
		return success(SuccessMessage.AVAILABLE_EMAIL);
	}

	@LoginCheck
	@GetMapping("/profile")
	public ResponseEntity<ResponseDto<UserResponseDto>> getProfile(@CurrentUser final Long id) {
		return success(memberService.getProfile(id));
	}

	@PostMapping(value = "/join")
	public ResponseEntity<ResponseDto<?>> join(@RequestBody @Valid final JoinDto joinDto) {
		return create(memberService.join(joinDto));
	}

	@PostMapping(value = "/login")
	public ResponseEntity<ResponseDto<?>> login(@RequestBody @Valid final LoginDto loginDto) {
		loginService.login(loginDto);
		return success(SuccessMessage.LOGIN_SUCCESS);
	}

	@LoginCheck
	@GetMapping("/logout")
	public ResponseEntity<ResponseDto<?>> logout() {
		loginService.logout();
		return success(SuccessMessage.LOGOUT_SUCCESS);
	}

	@LoginCheck
	@PutMapping
	public ResponseEntity<ResponseDto<?>> update(@CurrentUser final Long id,
		@RequestBody @Valid final UserUpdateRequestDto requestDto) {
		memberService.update(id, requestDto);
		return success(SuccessMessage.UPDATE_USER_SUCCESS);
	}

	@LoginCheck
	@DeleteMapping
	public ResponseEntity<ResponseDto<?>> delete(@CurrentUser final Long id,
		@RequestBody @Valid final UserDeleteRequestDto userDeleteRequestDto) {
		memberService.delete(id, userDeleteRequestDto);
		loginService.logout();
		return success(SuccessMessage.DELETE_USER_SUCCESS);
	}
}
