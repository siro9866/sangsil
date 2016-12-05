package com.mee.sangsil.common.exception;

@SuppressWarnings("serial")
public abstract class AbstractException extends Exception {

	private String code;
	private String message;

	private AbstractException(String message) {
		super(message);
		this.message = message;
	}

	protected AbstractException(String code, String message) {
		this(message);
		this.code = code;
	}

	private AbstractException(String message, Throwable err) {
		super(message, err);
		this.message = message;
	}

	protected AbstractException(String code, String message, Throwable err) {
		this(message, err);
		this.code = code;
	}

	protected AbstractException(CommonErrorCodable commonErrorCodable, String... args) {
		this(commonErrorCodable.getErrCode(), commonErrorCodable.getMessage(args));
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

}
