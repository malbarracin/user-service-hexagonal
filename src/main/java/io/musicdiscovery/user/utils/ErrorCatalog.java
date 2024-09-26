package io.musicdiscovery.user.utils;

import lombok.Getter;

@Getter
public enum ErrorCatalog {

	USER_NOT_FOUND("ERR_STUDENT_001", "User not found."),
	INVALID_USER("ERR_STUDENT_002", "Invalid user parameters."),
	GENERIC_ERROR("ERR_GEN_001", "An unexpected error occurred.");

	private final String code;
	private final String message;

	ErrorCatalog(String code, String message) {
		this.code = code;
		this.message = message;
	}
}
