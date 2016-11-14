package io.cde.account.domaim;

/**
 * @author lcl
 * @createDate 2016年11月1日下午2:16:25
 *
 */
//@Entity
//@Table(name = "f_mobile")
//@Component
public class Mobile extends BaseEntity {

  /**
  *电话所属用户id 
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

  //@Column(name = "f_user_id", nullable = false)
  public String getAccountId() {
    return accountId;
  }
  public void setAccountId(String accountId) {
    this.accountId = accountId;
  }
  //@Column(name = "f_mobile", nullable = false, length = 20)
  public String getMobile() {
    return mobile;
  }
  public void setMobile(String mobile) {
    this.mobile = mobile;
  }
  //@Column(name = "f_verified", nullable = true, length = 10)
  public boolean getIsVerified() {
    return isVerified;
  }
  public void setIsVerified(boolean isVerified) {
    this.isVerified = isVerified;
  }
}
