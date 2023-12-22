package com.example.kfanboy.global.member;

import static com.example.kfanboy.global.member.MemberDtoTest.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;

import com.example.kfanboy.global.AbstractControllerTest;
import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.exception.ErrorMessage;
import com.example.kfanboy.member.controller.MemberController;
import com.example.kfanboy.member.dto.JoinDto;
import com.example.kfanboy.member.dto.LoginDto;
import com.example.kfanboy.member.dto.UserDeleteRequestDto;
import com.example.kfanboy.member.dto.UserResponseDto;
import com.example.kfanboy.member.dto.UserUpdateRequestDto;
import com.example.kfanboy.member.service.LoginService;
import com.example.kfanboy.member.service.MemberService;

@DisplayName("회원 테스트")
@WebMvcTest(value = MemberController.class)
public class MemberControllerTest extends AbstractControllerTest {

	private final String BASIC_URL = "/api/member";

	@MockBean
	private MemberService memberService;
	@MockBean
	private LoginService loginService;

	@Test
	@DisplayName("해당 이메일 사용이 가능하다.")
	void email_notExists() throws Exception {
		// given
		String email = "aaa@naver.com";

		// when
		doNothing().when(memberService).isExistsEmail(email);

		// then
		mockMvc.perform(get(BASIC_URL + "/email-exists")
				.contentType(MediaType.APPLICATION_JSON)
				.param("email", email))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				queryParameters(
					parameterWithName("email").description("이메일")
				),
				defaultResponseFields()));
	}

	@Test
	@DisplayName("해당 이메일 사용이 불가능하다.")
	void email_exists() throws Exception {
		// given
		String email = "bbb@naver.com";

		// when
		doThrow(new CustomException(ErrorMessage.DUPLICATION)).when(memberService)
			.isExistsEmail(email);

		// then
		mockMvc.perform(get(BASIC_URL + "/email-exists")
				.contentType(MediaType.APPLICATION_JSON)
				.param("email", email))
			.andExpect(status().is4xxClientError())
			.andDo(restDocs.document(
				queryParameters(
					parameterWithName("email").description("이메일")
				),
				errorResponseFields()));
	}

	@Test
	@DisplayName("회원 상세 정보를 가져오다.")
	void getProfile() throws Exception {
		// given
		UserResponseDto userResponseDto = getUserResponseDto();

		// when
		given(memberService.getProfile(any())).willReturn(userResponseDto);

		// then
		mockMvc.perform(get(BASIC_URL + "/profile"))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				customResponseFields(
					List.of(fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("아이디"),
						fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath("data.userRole").type(JsonFieldType.STRING).description("계정역할")))
			));
	}

	@Test
	@DisplayName("회원 가입에 성공하다.")
	void join_success() throws Exception {
		// given
		JoinDto joinDto = getJoinDto();

		// when
		doNothing().when(memberService).join(joinDto);

		// then
		mockMvc.perform(post(BASIC_URL + "/join").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(joinDto)))
			.andExpect(status().isCreated())
			.andDo(restDocs.document(
				requestFields(fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
					fieldWithPath("password2").type(JsonFieldType.STRING).description("패스워드 확인"),
					fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"))
				, defaultResponseFields()));
	}

	@Test
	@DisplayName("로그인에 성공하다.")
	void login_success() throws Exception {
		// given
		LoginDto loginDto = getLoginDto();

		// when
		doNothing().when(loginService).login(loginDto);

		// then
		mockMvc.perform(post(BASIC_URL + "/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDto)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"))
				, defaultResponseFields()));
	}

	@Test
	@DisplayName("로그인에 실패하다.")
	void login_fail() throws Exception {
		// given
		LoginDto loginDto = getLoginDto();

		// when
		doThrow(new CustomException(ErrorMessage.USER_NOT_FOUND)).when(loginService)
			.login(loginDto);

		// then
		mockMvc.perform(post(BASIC_URL + "/login").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(loginDto)))
			.andExpect(status().is4xxClientError())
			.andDo(restDocs.document(
				requestFields(fieldWithPath("email").type(JsonFieldType.STRING).description("이메일"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드")),
				errorResponseFields()));
	}

	@Test
	@DisplayName("로그아웃을 하다.")
	void logout() throws Exception {
		// given nothing

		// when
		doNothing().when(loginService).logout();

		// then
		mockMvc.perform(get(BASIC_URL + "/logout"))
			.andExpect(status().isOk())
			.andDo(restDocs.document(defaultResponseFields()));
	}

	@Test
	@DisplayName("회원 정보를 수정하다.")
	void update_profile() throws Exception {
		// given
		UserUpdateRequestDto userUpdateRequestDto = getUserUpdateRequestDto();

		// when
		given(memberService.update(any(), any())).willReturn(getUserResponseDto());

		// then
		mockMvc.perform(put(BASIC_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userUpdateRequestDto)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(
					fieldWithPath("nickName").type(JsonFieldType.STRING).description("닉네임"),
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"),
					fieldWithPath("password2").type(JsonFieldType.STRING).description("패스워드 확인")),
				customResponseFields(
					List.of(fieldWithPath("data.id").type(JsonFieldType.NUMBER).description("아이디"),
						fieldWithPath("data.email").type(JsonFieldType.STRING).description("이메일"),
						fieldWithPath("data.nickName").type(JsonFieldType.STRING).description("닉네임"),
						fieldWithPath("data.userRole").type(JsonFieldType.STRING).description("계정역할")))
			));
	}

	@Test
	@DisplayName("회원 정보를 삭제하다.")
	void delete_profile() throws Exception {
		// given
		UserDeleteRequestDto userDeleteRequestDto = getUserDeleteRequestDto();

		// when
		doNothing().when(memberService).delete(any(), any());

		// then
		mockMvc.perform(delete(BASIC_URL)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(userDeleteRequestDto)))
			.andExpect(status().isOk())
			.andDo(restDocs.document(
				requestFields(
					fieldWithPath("password").type(JsonFieldType.STRING).description("패스워드"))
				, defaultResponseFields()
			));
	}
}
