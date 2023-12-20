package com.example.kfanboy.category.domain.entity;

import org.hibernate.validator.constraints.Length;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "category")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "category_id")
	private Long categoryId;

	@NotNull
	@Length(min = 1, max = 50)
	@Column(name = "category_name", nullable = false)
	private String categoryName;

	@Builder
	public Category(Long categoryId, String categoryName) {
		this.categoryId = categoryId;
		this.categoryName = categoryName;
	}
}
