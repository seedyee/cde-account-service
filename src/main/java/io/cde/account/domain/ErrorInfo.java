package io.cde.account.domain;

import java.io.Serializable;

/**
 * @author lcl
 */
public class ErrorInfo implements Serializable {

    /**
     * 序列化id.
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

    /**
     * 无参构造器.
     */
    public ErrorInfo() {
    }

    /**
     * 构造器.
     *
     * @param code 错误码
     * @param message 错误信息
     */
    public ErrorInfo(final int code, final String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
