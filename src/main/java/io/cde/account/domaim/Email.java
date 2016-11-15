package io.cde.account.domaim;

/**
 * 用户邮箱实体
 * @author lcl
 *
 */
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

  public String getAccountId() {
    return accountId;
  }
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }
  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }
  public boolean getIsVerified() {
    return isVerified;
  }
  public void setIsVerified(boolean isVerified) {
    this.isVerified = isVerified;
  }
}
