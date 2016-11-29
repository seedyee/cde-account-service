package io.cde.account.dao;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.cde.account.domain.Account;

/**
 * @author lcl
 * @createDate 2016年11月13日下午9:53:44
 * 连接数据库，查询用户信息
 */
public interface AccountRepository extends MongoRepository<Account, String> {
	/**
	 * 根据用户名查询用户信息.
	 * @param name 用户名
	 * @return 返回查询出的用户信息
	 */
	Account findByName(String name);
	
	/**
	 * 根据id查询用户信息.
	 * @param id 用户id
	 * @return 返回查询出的用户信息
	 */
	Account findById(String id);
}
