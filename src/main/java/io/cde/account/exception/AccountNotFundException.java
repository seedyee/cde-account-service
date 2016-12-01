package io.cde.account.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author lcl
 * @createDate 2016年12月1日上午10:29:03
 *
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Not found the account by id")
public class AccountNotFundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7653664292819989061L;

	private int code;
	
	private String message;
	
	/**
	 * 无参数构造器
	 */
	public AccountNotFundException() {}
	
	public AccountNotFundException(int code, String message) {
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
