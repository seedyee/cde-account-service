package io.cde.account.domaim;

/**
 * 用户邮箱实体
 * @author lcl
 *
 */
//@Entity
//@Table(name = "f_email")
//@Component
public class Email extends BaseEntity{

  /**
  * 邮箱所属用户id
  */
  private String accountId;
  /**
  * 邮箱的地址
  */
  private String email;
  /**
  *是否认证 认证为1，其他为0 
  */
  private boolean isVerified;

  //@Column(name = "f_user_id",nullable = false, length = 100)
  public String getAccountId() {
    return accountId;
  }
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }
  //@Column(name = "f_email", nullable = false, length = 50)
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  //@Column(name = "f_verified", nullable = true, length = 10)
  public boolean getIsVerified() {
    return isVerified;
  }
  public void setIsVerified(boolean isVerified) {
    this.isVerified = isVerified;
  }
}
