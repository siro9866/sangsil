package com.mee.sangsil.common.exception;

public enum CommonErrorCode implements CommonErrorCodable {

	/** %1은(는) 필수 입력 항목입니다. */
	ERR_0000("ERR_0000", "SUCCESS", "성공"),
	/** %1은(는) 필수 입력 항목입니다. */
	ERR_0001("ERR_0001", "FAIL", "%1은(는) 필수 입력 항목입니다."),
	/** %1은(는) 존재하지 않습니다. */
	ERR_0002("ERR_0002", "FAIL", "%1이(가) 존재하지 않습니다."),
	/** %1이(가) 불일치합니다. */
	ERR_0003("ERR_0003", "FAIL", "%1이(가) 불일치합니다."),
	
	ERR_9999("ERR_9999", "FAIL", "실패");

	private String errCode;
	private String errName;
	private String msg;

	@Override
	public String getErrCode() {
		return this.errCode;
	}
	
	@Override
	public String getErrName() {
		return this.errName;
	}

	@Override
	public String getMessage(String... args) {
		return CommonErrCodeUtil.parseMessage(this.msg, args);
	}

	CommonErrorCode(String errCode, String errName, String msg) {
		this.errCode = errCode;
		this.errName = errName;
		this.msg = msg;
	}

}
