package com.example.kfanboy.member.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.validator.constraints.Length;

import com.example.kfanboy.global.common.BaseTimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, unique = true, length = 50)
	@Length(min = 1, max = 50)
	@NotBlank
	@Email
	private String email;

	@Column(nullable = false, length = 30)
	@Length(min = 8, max = 30)
	@NotBlank
	private String password;

	@Column(name = "nick_name", nullable = false, unique = true, length = 30)
	@NotBlank
	private String nickName;

	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;

	@Column(name = "unregistered_at")
	private LocalDateTime unregisteredAt;

	@Enumerated(EnumType.STRING)
	@NotBlank
	@Column(name = "user_role", nullable = false, length = 10)
	private UserRole userRole;

	@Builder
	public Member(Long id, String email, String password, String nickName, LocalDateTime lastLoginAt,
		LocalDateTime unregisteredAt, UserRole userRole) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.nickName = nickName;
		this.lastLoginAt = lastLoginAt;
		this.unregisteredAt = unregisteredAt;
		this.userRole = userRole;
	}
}
