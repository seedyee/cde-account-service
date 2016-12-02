package io.cde.account.exception;

/**
 * @author lcl
 * 业务异常
 */
public class BizException extends Exception {
    
	/**
	 * 序列号.
	 */
	private static final long serialVersionUID = -5937698721553801873L;
	/**
	 * 异常编码. 
	 */
	private int code;
	/**
	 * 异常信息.
	 */
	private String message;
	
	public BizException(int code, String message) {
		this.code = code;
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
