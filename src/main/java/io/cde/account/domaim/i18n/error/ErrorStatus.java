package io.cde.account.domaim.i18n.error;

/**
 * @author lcl
 * @createDate 2016年11月21日上午8:25:17
 *
 */
public enum ErrorStatus {
	INVAILD_PASSWORD(100001);
	
	private int code;
	
	private ErrorStatus(int code) {
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}
