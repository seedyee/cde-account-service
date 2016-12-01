package io.cde.account.domain;

import java.io.Serializable;

/**
 * @author lcl
 * @createDate 2016年12月1日上午9:37:31
 *
 */
public class ErrorInfo implements Serializable{
    
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -3006736254030571502L;

	/**
	 * 错误码.
	 */
    private int code;
    
    /**
     * 错误信息.
     */
    private String message;
    
    public ErrorInfo() {}
    
    public ErrorInfo(int code, String message) {
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
