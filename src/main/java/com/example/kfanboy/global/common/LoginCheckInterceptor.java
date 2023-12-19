package com.example.kfanboy.global.common;

import java.util.Arrays;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.example.kfanboy.global.common.annotation.LoginCheck;
import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.exception.ErrorMessage;
import com.example.kfanboy.member.domain.entity.UserRole;
import com.example.kfanboy.member.service.LoginService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class LoginCheckInterceptor implements HandlerInterceptor {
	private final LoginService loginService;
	private final Environment environment;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// 테스트 환경에서는 인터셉트가 동작하지 않도록 설정
		Optional<String> profile = Arrays.stream(environment.getActiveProfiles()).findFirst();
		if (profile.isPresent() && StringUtils.equals(profile.get(), "test")) {
			return true;
		}

		if (handler instanceof HandlerMethod handlerMethod) {
			LoginCheck loginCheck = handlerMethod.getMethodAnnotation(LoginCheck.class);

			// LoginCheck 어노테이션 여부 체크
			if (null == loginCheck) {
				return true;
			}

			// 현재 로그인 세션 체크
			loginService.getCurrentUser()
				.orElseThrow(() -> new CustomException(ErrorMessage.NOT_LOGIN));

			// 관리자 권한 체크
			if (loginCheck.role() == UserRole.ROLE_ADMIN) {
				loginService.getCurrentRole()
					.orElseThrow(() -> new CustomException(ErrorMessage.NOT_AUTHORIZED));
			}
		}
		return true;
	}
}
