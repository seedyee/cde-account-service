package io.cde.account.domaim;

import java.util.Date;

import org.springframework.data.annotation.Transient;

/**
 * 用户实体
 * 
 * @author lcl
 *
 */

public class Account extends BaseEntity {

	/**
	 * 用户名
	 */
	private String name;
	/**
	 * 用户密码
	 */
	private String password;

	private String newPassword;
	/**
	 * 用户头像地址
	 */
	private String avatar;
	/**
	 * 所在公司
	 */
	private String company;
	/**
	 * 公司地址
	 */
	private String companyAddress;
	/**
	 * 行业
	 */
	private String companyBusiness;
	/**
	 * 职位
	 */
	private String position;
	/**
	 * 个人简介
	 */
	private String personal;
	/**
	 * 注册时间
	 */
	private Date timestamp;
	/**
	 * 用户真实姓名
	 */
	private String realName;
	/**
	 * 校验码，保留字段，不存到数据库中
	 */
	@Transient
	private String verifyCode;
	/**
	 * 用户默认邮箱
	 */
	private String email;
	/**
	 * 用户默认电话号码
	 */
	private String mobile;
	/**
	 * 默认邮箱是否公开，1：公开
	 */
	private boolean isPublicEmail;
	/**
	 * 默认电话号码是否公开，1：公开
	 */
	private boolean isPublicMobile;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// @Column(name = "f_password", nullable = false, length = 50)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@Transient
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	// @Column(name = "f_avatar", nullable = true, length = 300)
	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	// @Column(name = "f_company", nullable = true, length = 50)
	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	// @Column(name = "f_company_address", nullable = true, length = 200)
	public String getCompanyAddress() {
		return companyAddress;
	}

	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}

	// @Column(name ="f_company_business", nullable = true, length = 50)
	public String getCompanyBusiness() {
		return companyBusiness;
	}

	public void setCompanyBusiness(String companyBusiness) {
		this.companyBusiness = companyBusiness;
	}

	// @Column(name = "f_position", nullable = true, length = 50)
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	// @Column(name = "f_personal", nullable = true, length = 300)
	public String getPersonal() {
		return personal;
	}

	public void setPersonal(String personal) {
		this.personal = personal;
	}

	// @Column(name = "f_timestamp", nullable = true)
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	// @Column(name = "f_real_name", nullable = true, length = 50)
	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	// @Transient
	public String getVerifyCode() {
		return verifyCode;
	}

	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}

	// @Column(name = "f_email", nullable = true, length = 50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	// @Column(name = "f_mobile", nullable = true, length = 50)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	// @Column(name = "f_public_email", nullable = true, length = 10)
	public boolean getIsPublicEmail() {
		return isPublicEmail;
	}

	public void setIsPublicEmail(boolean isPublicEmail) {
		this.isPublicEmail = isPublicEmail;
	}

	// @Column(name = "f_public_mobile", nullable = true, length = 10)
	public boolean getIsPublicMobile() {
		return isPublicMobile;
	}

	public void setIsPublicMobile(boolean isPublicMobile) {
		this.isPublicMobile = isPublicMobile;
	}
}