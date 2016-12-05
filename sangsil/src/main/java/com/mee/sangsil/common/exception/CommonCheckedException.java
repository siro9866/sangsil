package com.mee.sangsil.common.exception;

@SuppressWarnings("serial")
public class CommonCheckedException extends AbstractException {

	public CommonCheckedException(String code, String message) {
		super(code, message);
	}

	public CommonCheckedException(String code, String message, Throwable err) {
		super(code, message, err);
	}

	public CommonCheckedException(CommonErrorCodable commonErrorCodable, String... args) {
		super(commonErrorCodable, args);
	}

}
