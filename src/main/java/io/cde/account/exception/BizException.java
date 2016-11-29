package io.cde.account.exception;

/**
 * @author lcl
 * @createDate 2016年11月29日下午1:59:24
 * 业务异常
 */
public class BizException extends Exception {

	private static final long serialVersionUID = -5937698721553801873L;
	
	public BizException(String message) {
		super(message);
	}
	
	public BizException(String message, Throwable cause) {
		super(message, cause);
	}
}
