package io.cde.account.domaim;

import org.springframework.data.annotation.Id;

/**
 * @author lcl
 * @createDate 2016年11月1日下午12:53:38 所有用户信息的基类
 */
public class BaseEntity {

	/**
	 * 主键id
	 */
	@Id
	private String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
