package io.cde.account.dao;

import java.util.List;

import io.cde.account.domain.Mobile;

/**
 * @author lcl
 * @createDate 2016年11月29日下午5:41:15
 *
 */
public interface MobileDao {
    
	/**
	 * 根据用户id和邮箱id获取电话信息.
	 * 
	 * @param accountId 用户id
	 * @param mobileId 电话id
	 * @return 操作成功返回电话信息，操作失败返回null
	 */
	Mobile getMobileById(String accountId, String mobileId);
	
	/**
	 * 根据电话号码检查电话号码是否被使用过.
	 * 
	 * @param mobile 电话号码
	 * @return 查询到返回true，否则返回false
	 */
	boolean isMobileExisted(String mobile);
	
	/**
	 * 获取用户的电话信息.
	 * 
	 * @param accountId 用户id
	 * @return 返回用户电话信息
	 */
	List<Mobile> getMobiles(String accountId);
	
	/**
	 * 修改电话信息.
	 * 
	 * @param accountId 用户id
	 * @param mobile 要修改的电话信息
	 * @return 修改成功返回1，否则返回-1
	 */
	int updateMobile(String accountId, Mobile mobile);
	
	/**
	 * 添加电话信息.
	 * 
	 * @param accountId 用户id
	 * @param mobile 电话信息
	 * @return 操作成功返回1，否则返回-1
	 */
	int addMobile(String accountId, Mobile mobile);
	
	/**
	 * 删除电话信息.
	 * 
	 * @param accountId 用户id
	 * @param mobileId 邮箱id
	 * @return 操作成功返回1，否则返回-1
	 */
	int deleteMobile(String accountId, String mobileId);
}
