package io.cde.account.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
/**
 * 用户邮箱实体
 * @author lcl
 *
 */
@Entity
@Table(name = "f_email")
@Component
public class Email extends BaseClass{

  private static final long serialVersionUID = 1L;
  /**
  * 邮箱所属用户id
  */
  private String userId;
  /**
  * 邮箱的地址
  */
  private String email;
  /**
  *是否认证 认证为1，其他为0 
  */
  private String isVerified;

  @Column(name = "f_user_id",nullable = false, length = 100)
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  @Column(name = "f_email", nullable = false, length = 50)
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  @Column(name = "f_verified", nullable = true, length = 10)
  public String getIsVerified() {
    return isVerified;
  }
  public void setIsVerified(String isVerified) {
    this.isVerified = isVerified;
  }
}
