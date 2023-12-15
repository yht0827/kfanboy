package com.example.kfanboy.global.util;

public interface PasswordEncryptor {
	String encrypt(String password);

	boolean isMatch(String password, String hashedPassword);
}
