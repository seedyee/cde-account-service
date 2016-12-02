package io.cde.account.domain.i18n;

/**
 * @author lcl
 *
 */
public enum SystemError {
	/**
     * 插入数据失败.
     */
    INSERT_FAILED(10),
    /**
     * 更新数据失败.
     */
    UPDATE_FAILED(20),
    /**
     * 删除数据失败.
     */
    DELETE_FAILED(30);
	
	/**
	 * 错误码.
	 */
	private int code;
	
	SystemError(int code) {
		this.code = ServiceCode.SYSTEM.getCode() * 10000 + code;
	}
	
	/**
     * 获取错误码
     * @return
     */
    public int getCode() {
		return code;
	}
}
