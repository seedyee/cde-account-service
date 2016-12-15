package io.cde.account.dao;

import io.cde.account.domain.Account;
import io.cde.account.domain.Email;

/**
 * @author lcl
 *
 */

public interface EmailDao {
	
	/**
	 * 根据用户id和邮箱id判断该用户是否关联该邮箱.
	 * 
	 * @param accountId 用户id
	 * @param emailId 邮箱id
	 * @return 返回该用户信息
	 */
	Account findAccountByEmailId(String accountId, String emailId);
    
    /**
     * 根据邮箱地址查询邮箱信息.
     * 
     * @param email 邮箱地址
     * @return 查询到则返回true，否则返回false
     */
    boolean isEmailExisted(String email);
    
    
    /**
     * 修改邮箱信息.
     * 
     * @param 用户id
     * @param emailId 邮箱id
     * @param isVerified 是否认证字段
     * @return 修改操作成功返回1，否则返回-1
     */
    int updateEmail(String accountId, String emailId, boolean isVerified);
    
    /**
     * 设置默认邮箱是否是公开.
     * 
     * @param accountId 用户id
     * @param isPublic 是否公开
     * @return 修改操作成功返回1，否者返回-1
     */
    int updatePublicEmail(String accountId, boolean isPublic);
    /**
     * 为指定的用户添加邮箱.
     * 
     * @param accountId 用户id
     * @param email 要添加的邮箱的信息
     * @return 添加操作成功返回1，否则返回-1
     */
    int addEmail(String accountId, Email email);
    
    /**
     * 删除指定用户的邮箱信息.
     * 
     * @param accountId 用户id
     * @param emailId 邮箱id
     * @return 删除操作成功返回1，否则返回-1
     */
    int deleteEmail(String accountId, String emailId);
}
