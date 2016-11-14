/**
 * 
 */
package io.cde.account.dao.Interface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.cde.account.domaim.Mobile;

/**
 * @author lcl
 * @createDate 2016年11月13日下午9:56:07
 *
 */
public interface MobileRepository extends MongoRepository<Mobile, String>{
	/**
	 * 根据用户id获取
	 * @param accountId
	 * @return
	 */
	List<Mobile> findByAccountId(String accountId);
	
	/**
	 * 根据邮箱id获取邮箱信息 
	 * @param id
	 * @return
	 */
	Mobile findById(String id);
	
	/**
	 * 根据电话号码获取电话号码信息
	 * @param mobile
	 * @return
	 */
	Mobile findByMobile(String mobile);
}
