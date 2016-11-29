package io.cde.account.domain;

import java.util.Date;

import org.springframework.data.annotation.Transient;

/**
 * 用户实体
 * @author lcl
 * @createDate 2016年11月14日上午11:06:17
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
	/**
	 * 新密码，由于修改密码的保留字段，不存入数据库
	 */
	@Transient
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
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNewPassword() {
		return newPassword;
	}
	
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	
	public String getAvatar() {
		return avatar;
	}
	
	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}
	
	public String getCompany() {
		return company;
	}
	
	public void setCompany(String company) {
		this.company = company;
	}
	
	public String getCompanyAddress() {
		return companyAddress;
	}
	
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	
	public String getCompanyBusiness() {
		return companyBusiness;
	}
	
	public void setCompanyBusiness(String companyBusiness) {
		this.companyBusiness = companyBusiness;
	}
	
	public String getPosition() {
		return position;
	}
	
	public void setPosition(String position) {
		this.position = position;
	}
	
	public String getPersonal() {
		return personal;
	}
	
	public void setPersonal(String personal) {
		this.personal = personal;
	}
	
	public Date getTimestamp() {
		return timestamp;
	}
	
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public String getRealName() {
		return realName;
	}
	
	public void setRealName(String realName) {
		this.realName = realName;
	}
	
	public String getVerifyCode() {
		return verifyCode;
	}
	
	public void setVerifyCode(String verifyCode) {
		this.verifyCode = verifyCode;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public String getMobile() {
		return mobile;
	}
	
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	
	public boolean getIsPublicEmail() {
		return isPublicEmail;
	}
	
	public void setIsPublicEmail(boolean isPublicEmail) {
		this.isPublicEmail = isPublicEmail;
	}
	
	public boolean getIsPublicMobile() {
		return isPublicMobile;
	}
	
	public void setIsPublicMobile(boolean isPublicMobile) {
		this.isPublicMobile = isPublicMobile;
	}
}