package io.cde.account.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
/**
 * @author lcl
 * @createDate 2016年11月1日下午2:16:25
 *
 */
@Entity
@Table(name = "f_mobile")
@Component
public class Mobile extends BaseClass {

  private static final long serialVersionUID = 1L;
  /**
  *电话所属用户id 
  */
  private String userId;
  /**
  * 电话号码
  */
  private String mobile;
  /**
  * 是否认证 认证为1，不认证为0，默认为0
  */
  private String isVerified;

  @Column(name = "f_user_id", nullable = false)
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  @Column(name = "f_mobile", nullable = false, length = 20)
  public String getMobile() {
    return mobile;
  }
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  @Column(name = "f_verified", nullable = true, length = 10)
  public String getIsVerified() {
    return isVerified;
  }
  public void setIsVerified(String isVerified) {
    this.isVerified = isVerified;
  }
}
