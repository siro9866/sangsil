package com.mee.sangsil.common.exception;

public interface CommonErrorCodable {
	String getErrCode();
	String getErrName();
	String getMessage(String... args);
}
