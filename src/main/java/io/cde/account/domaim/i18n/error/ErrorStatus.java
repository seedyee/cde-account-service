package io.cde.account.domaim.i18n.error;

/**
 * @author lcl
 * @createDate 2016年11月21日上午8:25:17
 * 错误返回信息枚举
 */
public enum ErrorStatus {
	ACCOUNT_EXISTED(1000001),
	ILLEGAL_ACCOUNT_PASSWORD(1000020),
	EMAIL_NOT_VERIFIED(1000040),
	ACCOUNT_NOT_EMAIL(1000060),
	MOBILE_NOT_VERIFIED(1000080),
	ACCOUNT_NOT_MOBILE(1000100),
	EMAIL_EXISTED(1000120),
	MOBILE_EXISTED(1000140),
	ILLEGAL_DELETE_EMAIL(1000160),
	ILLEGAL_DELETE_MOBILE(1000180);
	
	private int code;
	
	private ErrorStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
