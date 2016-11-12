package io.cde.account.beans;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.springframework.stereotype.Component;
/**
 * @author lcl
 * @createDate 2016年11月4日下午2:14:43
 * 安全日志实体
 */
@Entity
@Table(name = "f_log")
@Component
public class Log extends BaseClass {

  private static final long serialVersionUID = 1L;
  /**
  * 安全日志所属用户名
  */
  private String user;
  /**
  * 操作
  */
  private String action;
  /**
  * 操作人
  */
  private String actor;
  /**
  * 操作机器id
  */
  private String actorIp;
  /**
  * 操作的当前位置
  */
  private String actorLocation;

  @Column(name = "f_user", nullable = false)
  public String getUser() {
    return user;
  }
  public void setUser(String user) {
    this.user = user;
  }
  @Column(name = "f_action", nullable = true, length = 100)
  public String getAction() {
    return action;
  }
  public void setAction(String action) {
    this.action = action;
  }
  @Column(name = "f_actor", nullable = true, length = 100)
  public String getActor() {
    return actor;
  }
  public void setActor(String actor) {
    this.actor = actor;
  }
  @Column(name = "f_actor_ip", nullable = true, length = 100)
  public String getActorIp() {
    return actorIp;
  }
  public void setActorIp(String actorIp) {
    this.actorIp = actorIp;
  }
  @Column(name = "f_actor_location", nullable = true, length = 100)
  public String getActorLocation() {
    return actorLocation;
  }
  public void setActorLocation(String actorLocation) {
    this.actorLocation = actorLocation;
  }
}
