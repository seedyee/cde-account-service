/**
 * 
 */
package io.cde.account.dao.Interface;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.cde.account.domaim.Account;

/**
 * @author lcl
 * @createDate 2016年11月13日下午9:53:44
 * 连接数据库，查询用户信息
 */
public interface AccountRepository extends MongoRepository<Account, String> {
	/**
	 * 根据用户名查询用户信息
	 * @param name
	 * @return
	 */
	Account findByName(String name);
	
	/**
	 * 根据id查询用户信息
	 * @param id
	 * @return
	 */
	Account findById(String id);
}
