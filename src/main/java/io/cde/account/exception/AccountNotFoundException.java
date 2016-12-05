package io.cde.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author lcl
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found the account by id")
public class AccountNotFoundException extends RuntimeException {

	/**
	 * 序列号.
	 */
	private static final long serialVersionUID = -7653664292819989061L;
    /**
     * 异常编码.
     */
	private int code;
	/**
	 * 异常信息.
	 */
	private String message;
	
	/**
	 * 无参数构造器.
	 */
	public AccountNotFoundException() {}
	
	/**
	 * 有参数构造器.
	 * 
	 * @param code 异常编码
	 * @param message 异常信息
	 */
	public AccountNotFoundException(int code, String message) {
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
