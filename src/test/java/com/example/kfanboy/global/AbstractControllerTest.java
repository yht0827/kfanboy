package com.example.kfanboy.global;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationContextProvider;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.restdocs.payload.ResponseFieldsSnippet;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import com.fasterxml.jackson.databind.ObjectMapper;

@Disabled
@WebMvcTest
@ExtendWith({RestDocumentationExtension.class})
@Import(RestDocsConfig.class)
@ActiveProfiles("test")
public abstract class AbstractControllerTest {
	@Autowired
	protected ObjectMapper objectMapper;

	@Autowired
	protected RestDocumentationResultHandler restDocs;

	protected MockMvc mockMvc;

	/**
	 * response body 한글이 깨지는 문제를 처리 하기 위해서 encoding 필터를 적용함.
	 * @param webApplicationContext
	 * @param restDocumentationContextProvider
	 */
	@BeforeEach
	void setUp(WebApplicationContext webApplicationContext,
		RestDocumentationContextProvider restDocumentationContextProvider) {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
			.apply(documentationConfiguration(restDocumentationContextProvider))
			.alwaysDo(MockMvcResultHandlers.print())
			.alwaysDo(restDocs)
			.addFilters(new CharacterEncodingFilter("UTF-8", true))
			.build();
	}

	protected ResponseFieldsSnippet defaultResponseFields() {
		return responseFields(getDefaultResponseFields());
	}

	protected ResponseFieldsSnippet customResponseFields(List<FieldDescriptor> fieldDescriptors) {
		List<FieldDescriptor> list = getResponseFields();
		list.addAll(fieldDescriptors);
		return responseFields(list);
	}

	protected ResponseFieldsSnippet errorResponseFields() {
		return responseFields(List.of(
			fieldWithPath("result").type(JsonFieldType.BOOLEAN).description("결과"),
			fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"),
			fieldWithPath("errorList").type(JsonFieldType.ARRAY).description("에러리스트")));
	}

	protected List<FieldDescriptor> getResponseFields() {
		List<FieldDescriptor> list = new ArrayList<>();
		list.add(fieldWithPath("result").type(JsonFieldType.BOOLEAN).description("결과"));
		list.add(fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"));
		return list;
	}

	protected List<FieldDescriptor> getDefaultResponseFields() {
		List<FieldDescriptor> list = new ArrayList<>();
		list.add(fieldWithPath("result").type(JsonFieldType.BOOLEAN).description("결과"));
		list.add(fieldWithPath("data").type(JsonFieldType.NULL).description("데이터"));
		list.add(fieldWithPath("message").type(JsonFieldType.STRING).description("메세지"));
		return list;
	}

	protected ResponseFieldsSnippet customPageResponseFields(List<FieldDescriptor> fieldDescriptors) {
		List<FieldDescriptor> list = getResponseFields();
		list.addAll(fieldDescriptors);
		list.add(fieldWithPath("data.totalPage").ignored());
		list.add(fieldWithPath("data.pageSize").ignored());
		list.add(fieldWithPath("data.totalElements").ignored());
		list.add(fieldWithPath("data.pageNumber").ignored());
		return responseFields(list);
	}

	protected ResponseFieldsSnippet customSliceResponseFields(List<FieldDescriptor> fieldDescriptors) {
		List<FieldDescriptor> list = getResponseFields();
		list.addAll(fieldDescriptors);
		list.add(fieldWithPath("data.size").type(JsonFieldType.NUMBER).description("json 데이터 개수"));
		list.add(fieldWithPath("data.totalElements").type(JsonFieldType.NUMBER).description("전체 데이터 개수"));
		list.add(fieldWithPath("data.last").type(JsonFieldType.BOOLEAN).description("마지막 데이터 여부"));
		return responseFields(list);
	}

}
