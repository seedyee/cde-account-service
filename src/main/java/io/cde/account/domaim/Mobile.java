package io.cde.account.domaim;

import org.springframework.data.annotation.Transient;

/**
 * @author lcl
 * @createDate 2016年11月1日下午2:16:25
 *
 */
public class Mobile extends BaseEntity {

	/**
	 * 电话所属用户id
	 */
	private String accountId;
	/**
	 * 电话号码
	 */
	private String mobile;
	/**
	 * 是否认证 认证为1，不认证为0，默认为0
	 */
	private boolean isVerified;
	/**
	 * 是否默认，保留字段
	 */
	private boolean isDefault;
	/**
	 * 是否公开，保留字段
	 */
	private boolean isPublic;
	
	public String getAccountId() {
		return accountId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public boolean getIsVerified() {
		return isVerified;
	}

	public void setIsVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
	@Transient
	public boolean getIsDefault() {
		return isDefault;
	}

	public void setIsDefault(boolean isDefault) {
		this.isDefault = isDefault;
	}
	@Transient
	public boolean getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(boolean isPublic) {
		this.isPublic = isPublic;
	}
	
}
