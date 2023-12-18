package com.example.kfanboy.global.util;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import com.example.kfanboy.global.exception.CustomException;
import com.example.kfanboy.global.exception.ErrorMessage;

@Component
public class BcryptPasswordEncryptor implements PasswordEncryptor {
	@Override
	public String encrypt(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	@Override
	public boolean isMatch(String password, String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);
	}

	@Override
	public void validatePassword(String inputPassword, String validPassword) {
		if (!isMatch(inputPassword, validPassword)) {
			throw new CustomException(ErrorMessage.INCORRECT_PASSWORD_OR_USER_EMAIL);
		}
	}
}
