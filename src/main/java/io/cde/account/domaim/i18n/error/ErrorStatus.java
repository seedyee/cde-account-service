package io.cde.account.domaim.i18n.error;

/**
 * @author lcl
 * @createDate 2016年11月21日上午8:25:17
 * 错误返回信息枚举
 */
public enum ErrorStatus {
    /**
    * 用户已存在.
    */
    ACCOUNT_EXISTED(1000001),
    /**
    * 用户名密码不匹配.
	*/
    ILLEGAL_ACCOUNT_PASSWORD(1000020),
    /**
	* 邮箱没有认证.
	*/
    EMAIL_NOT_VERIFIED(1000040),
    /**
	* 用户没有关联该邮箱.
	*/
    ACCOUNT_NOT_EMAIL(1000060),
    /**
	* 电话号码没有认证.
	*/
    MOBILE_NOT_VERIFIED(1000080),
    /**
	* 用户没有关联该电话号码.
	*/
    ACCOUNT_NOT_MOBILE(1000100),
    /**
	* 邮箱已经被使用过.
	*/
    EMAIL_EXISTED(1000120),
    /**
	* 电话已经被使用过.
	*/
    MOBILE_EXISTED(1000140),
    /**
	* 默认邮箱不能删除.
	*/
    ILLEGAL_DELETE_EMAIL(1000160),
    /**
	* 默认电话不能删除.
	*/
    ILLEGAL_DELETE_MOBILE(1000180);

    /**
	* 错误码.
	*/
    private int code;

    /**
    * 私有构造方法.
    * @param code 错误码
    */
    private ErrorStatus(int code) {
		this.code = code;
	}

    public int getCode() {
		return code;
	}
}
