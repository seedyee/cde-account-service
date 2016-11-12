package io.cde.account.beans;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
/**
 * @author lcl
 * @createDate 2016年11月1日下午12:53:38
 * 所有用户信息的基类
 */
@MappedSuperclass
public class BaseClass implements Serializable {

  private static final long serialVersionUID = 1L;
  /**
  * 主键id
  */
  private String id;
  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "io.cde.account.tools.UUIDUtils")
  @Column(name = "F_ID")
  public String getId() {
    return id;
  }
  public void setId(String id) {
    this.id = id;
  }	
}
