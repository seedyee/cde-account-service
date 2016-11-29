package io.cde.account.dao.Interface;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import io.cde.account.domain.Mobile;

/**
 * @author lcl
 * @createDate 2016年11月13日下午9:56:07
 *
 */
public interface MobileRepository extends MongoRepository<Mobile, String>{
	/**
	 * 根据用户id获取
	 * @param accountId 用户id
	 * @return 返回用户的电话信息的集合
	 */
	List<Mobile> findByAccountId(String accountId);
	
	/**
	 * 根据邮箱id获取电话信息 
	 * @param id 电话id
	 * @return 返回查询出来的电话对象
	 */
	Mobile findById(String id);
	
	/**
	 * 根据电话号码获取电话号码信息
	 * @param mobile 电话号码
	 * @return 返回查询出来的电话对象
	 */
	Mobile findByMobile(String mobile);
}
