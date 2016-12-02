package io.cde.account.domain.i18n;

/**
 * @author lcl
 *
 */
public enum Error {
	/**
    * 用户已存在.
    */
    ACCOUNT_EXISTE(10),
    /**
    * 用户名密码不匹配.
	*/
    UNMATCHED_ACCOUNT_AND_PASSWORD(20),
    /**
	* 邮箱没有认证.
	*/
    UNVERIFIED_EMAIL(30),
    /**
	* 用户没有关联该邮箱.
	*/
    UNASSOCIATED_ACCOUNT_AND_EMAIL(40),
    /**
	* 电话号码没有认证.
	*/
    UNVERIFIED_MOBILE(50),
    /**
	* 用户没有关联该电话号码.
	*/
    UNASSOCIATED_ACCOUNT_AND_MOBILE(60),
    /**
	* 邮箱已经被使用过.
	*/
    USED_EMAIL(70),
    /**
	* 电话已经被使用过.
	*/
    USED_MOBILE(80),
    /**
	* 默认邮箱不能删除.
	*/
    UNDELETABLE_DEFAULT_EMAIL(90),
    /**
	* 默认电话不能删除.
	*/
    UNDELETABLE_DEFAULT_MOBILE(100);

    /**
	* 错误码.
	*/
    private int code;

    /**
    * 私有构造方法,根据错误编号和服务编号生成错误码.
    * @param code 错误码
    */
    Error(int code) {
		this.code = ServiceCode.CDE_ACCOUNT_SERVICE.getCode() * 10000 + code;
	}
    
    /**
     * 获取错误码
     * @return
     */
    public int getCode() {
		return code;
	}
}
