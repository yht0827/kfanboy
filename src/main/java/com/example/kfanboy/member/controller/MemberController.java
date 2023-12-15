package com.example.kfanboy.member.controller;

import static com.example.kfanboy.global.common.response.ResponseHandler.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.kfanboy.global.common.SuccessMessage;
import com.example.kfanboy.global.common.annotation.LoginCheck;
import com.example.kfanboy.global.common.response.ResponseDto;
import com.example.kfanboy.member.dto.LoginDto;
import com.example.kfanboy.member.service.LoginService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/member")
public class MemberController {

	private final LoginService loginService;

	@PostMapping(value = "/login")
	public ResponseEntity<ResponseDto<?>> login(@RequestBody @Valid final LoginDto loginDto) {
		loginService.login(loginDto);
		return success(HttpStatus.OK, SuccessMessage.LOGIN_SUCCESS);
	}

	@LoginCheck
	@GetMapping("/logout")
	public ResponseEntity<ResponseDto<?>> logout() {
		loginService.logout();
		return success(HttpStatus.OK, SuccessMessage.LOGOUT_SUCCESS);
	}
}
