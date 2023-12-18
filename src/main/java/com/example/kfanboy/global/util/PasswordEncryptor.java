package com.example.kfanboy.global.util;

public interface PasswordEncryptor {
	String encrypt(String password);

	boolean isMatch(String password, String hashedPassword);

	void validatePassword(String inputPassword, String validPassword);
}
