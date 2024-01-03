package com.example.kfanboy.member.domain.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.hibernate.validator.constraints.Length;

import com.example.kfanboy.global.common.BaseTimeEntity;
import com.example.kfanboy.member.dto.UserUpdateRequestDto;

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
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "member")
@SQLRestriction("deleted_at is null")
@SQLDelete(sql = "update member set deleted_at=current_timestamp where member_id = ?")
public class Member extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "member_id")
	private Long memberId;

	@Email
	@NotBlank
	@Column(nullable = false, unique = true, length = 50)
	@Length(min = 1, max = 50)
	private String email;

	@NotBlank
	@Column(nullable = false, length = 100)
	@Length(min = 1, max = 100)
	private String password;

	@NotBlank
	@Column(name = "nick_name", nullable = false, unique = true, length = 30)
	@Length(min = 1, max = 30)
	private String nickName;

	@Column(name = "last_login_at")
	private LocalDateTime lastLoginAt;

	@Column(name = "unregistered_at")
	private LocalDateTime unregisteredAt;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "user_role", nullable = false)
	private UserRole userRole;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Builder
	public Member(Long memberId, String email, String password, String nickName, LocalDateTime lastLoginAt,
		LocalDateTime unregisteredAt, UserRole userRole, LocalDateTime deletedAt) {
		this.memberId = memberId;
		this.email = email;
		this.password = password;
		this.nickName = nickName;
		this.lastLoginAt = lastLoginAt;
		this.unregisteredAt = unregisteredAt;
		this.userRole = userRole;
		this.deletedAt = deletedAt;
	}

	public void updateLastLogin() {
		this.lastLoginAt = LocalDateTime.now();
	}

	public Member updateMember(UserUpdateRequestDto userUpdateRequestDto, String encryptPassword) {
		this.nickName = userUpdateRequestDto.nickName();
		this.password = encryptPassword;

		return this;
	}

}
