/**
 * 
 */
package io.cde.account.dao.Interface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.cde.account.domaim.Email;

/**
 * @author lcl
 * @createDate 2016年11月13日下午9:55:08
 * 连接数据库，查询邮箱信息
 */
public interface EmailRepository extends MongoRepository<Email, String>{
	
	/**
	 * 根据邮箱地址获取邮箱信息
	 * @param email
	 * @return
	 */
	Email findByEmail(String email);
	
	/**
	 * 根据邮箱id查询邮箱信息
	 * @param id
	 * @return
	 */
	Email findById(String id);
	
	/**
	 * 根据用户id获取用户邮箱信息
	 * @param accountId
	 * @return
	 */
	List<Email> findByAccountId(String accountId);
	
}
