package io.cde.account.domain.i18n;

/**
 * @author lcl
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
     * 用来计算错误码的常量.
     */
    private static final int SERVICE_COUNT = 10000;

    /**
     * 错误码.
     */
    private int code;

    /**
     * 构造器.
     *
     * @param code 错误码
     */
    SystemError(final int code) {
        this.code = ServiceCode.SYSTEM.getCode() * SERVICE_COUNT + code;
    }

    /**
     * 获取错误码.
     *
     * @return 返回错误码
     */
    public int getCode() {
        return code;
    }
}
