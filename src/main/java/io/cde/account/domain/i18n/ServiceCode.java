package io.cde.account.domain.i18n;

/**
 * @author lcl
 * @createDate 2016年11月29日上午9:54:42
 *
 */
public enum ServiceCode {
	CDE_ACCOUNT_SERVICE(100);
	
	/**
	 * 服务编号
	 */
	private int code;
	
	/**
	 * 构造方法
	 * @param code
	 */
	ServiceCode(int code) {
		this.code = code;
	}
	
	/**
	 * 获取服务编号
	 * @return
	 */
	public int getCode() {
		return code;
	}
}
