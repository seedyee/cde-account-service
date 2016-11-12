package io.cde.account.beans;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
/**
 * @author lcl
 * @createDate 2016年11月4日上午11:54:59
 * 用户会话信息实体
 */
@Entity
@Table(name = "f_session")
@Component
public class Session extends BaseClass{

  private static final long serialVersionUID = 1L;
  /**
  * 回话所属的用户id
  */
  private String userId;
  /**
  * 最后访问时间
  */
  private Date lastVisit;
  /**
  * 设备
  */
  private String equipment;
  /**
  * 位置
  */
  private String location;
  /**
  * 登录时间
  */
  private Date loginTime;

  @Column(name = "f_user_id", nullable = false, length = 100)
  public String getUserId() {
    return userId;
  }
  public void setUserId(String userId) {
    this.userId = userId;
  }
  @Column(name = "f_lastvisit", nullable = true)
  public Date getLastVisit() {
    return lastVisit;
  }
  public void setLastVisit(Date lastVisit) {
    this.lastVisit = lastVisit;
  }
  @Column(name = "f_equipment", nullable = true, length = 100)
  public String getEquipment() {
    return equipment;
  }
  public void setEquipment(String equipment) {
    this.equipment = equipment;
  }
  @Column(name = "f_location", nullable = true, length = 100)
  public String getLocation() {
    return location;
  }
  public void setLocation(String location) {
    this.location = location;
  }
  @Column(name = "f_logintime", nullable = true)
  public Date getLoginTime() {
    return loginTime;
  }
  public void setLoginTime(Date loginTime) {
    this.loginTime = loginTime;
  }
}
