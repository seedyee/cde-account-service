package io.cde.account.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.stereotype.Component;
/**
 * 用户实体
 * @author lcl
 *
 */
@Entity
@Table(name = "f_user")
@Component
public class User extends BaseClass{
	
  private static final long serialVersionUID = 1L;
  /**
  * 用户名
  */
  private String username;
  /**
  * 用户密码
  */
  private String password;
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
  private String isPublicEmail;
  /**
  * 默认电话号码是否公开，1：公开
  */
  private String isPublicMobile;

  @Column(name = "f_username", unique = true, nullable = false, length = 50)
  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
  this.username = username;
  }
  @Column(name = "f_password", nullable = false, length = 50)
  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }
  @Column(name = "f_avatar", nullable = true, length = 300)
  public String getAvatar() {
    return avatar;
  }
  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }
  @Column(name = "f_company", nullable = true, length = 50)
  public String getCompany() {
    return company;
  }
  public void setCompany(String company) {
    this.company = company;
  }
  @Column(name = "f_company_address", nullable = true, length = 200)
  public String getCompanyAddress() {
    return companyAddress;
  }
  public void setCompanyAddress(String companyAddress) {
    this.companyAddress = companyAddress;
  }
  @Column(name ="f_company_business", nullable = true, length = 50)
  public String getCompanyBusiness() {
    return companyBusiness;
  }
  public void setCompanyBusiness(String companyBusiness) {
    this.companyBusiness = companyBusiness;
  }
  @Column(name = "f_position", nullable = true, length = 50)
  public String getPosition() {
    return position;
  }
  public void setPosition(String position) {
    this.position = position;
  }
  @Column(name = "f_personal", nullable = true, length = 300)
  public String getPersonal() {
    return personal;
  }
  public void setPersonal(String personal) {
    this.personal = personal;
  }
  @Column(name = "f_timestamp", nullable = true)
  public Date getTimestamp() {
    return timestamp;
  }
  public void setTimestamp(Date timestamp) {
    this.timestamp = timestamp;
  }
  @Column(name = "f_real_name", nullable = true, length = 50)
  public String getRealName() {
    return realName;
  }
  public void setRealName(String realName) {
    this.realName = realName;
  }
  @Transient
  public String getVerifyCode() {
    return verifyCode;
  }
  public void setVerifyCode(String verifyCode) {
    this.verifyCode = verifyCode;
  }
  @Column(name = "f_email", nullable = true, length = 50)
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  @Column(name = "f_mobile", nullable = true, length = 50)
  public String getMobile() {
    return mobile;
  }
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  @Column(name = "f_public_email", nullable = true, length = 10)
  public String getIsPublicEmail() {
    return isPublicEmail;
  }
  public void setIsPublicEmail(String isPublicEmail) {
    this.isPublicEmail = isPublicEmail;
  }
  @Column(name = "f_public_mobile", nullable = true, length = 10)
  public String getIsPublicMobile() {
    return isPublicMobile;
  }
  public void setIsPublicMobile(String isPublicMobile) {
    this.isPublicMobile = isPublicMobile;
  }
}