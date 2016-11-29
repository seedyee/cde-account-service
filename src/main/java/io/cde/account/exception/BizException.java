package io.cde.account.exception;

import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author lcl
 * @createDate 2016年11月29日下午1:59:24
 * 业务异常
 */
public class BizException extends Exception {

	private static final long serialVersionUID = -5937698721553801873L;
	
	private int code;
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
