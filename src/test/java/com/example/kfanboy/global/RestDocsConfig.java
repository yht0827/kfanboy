package com.example.kfanboy.global;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.snippet.Attributes;

@TestConfiguration
public class RestDocsConfig {
	@Bean
	public RestDocumentationResultHandler restDocumentationResultHandler() {
		return MockMvcRestDocumentation.document(
			"{class-name}/{method-name}",
			Preprocessors.preprocessRequest(Preprocessors.prettyPrint()),
			Preprocessors.preprocessResponse(Preprocessors.prettyPrint()));
	}

	public static Attributes.Attribute field(final String key, final String value) {
		return new Attributes.Attribute(key, value);
	}
}
