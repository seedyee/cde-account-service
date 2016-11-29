package io.cde.account.dao;

import java.util.List;

import io.cde.account.domain.Email;

/**
 * @author lcl
 * @createDate 2016年11月29日下午3:33:42
 *
 */
public interface EmailDao {
	
	/**
	 * 根据用户id和邮箱id获取邮箱信息.
	 * 
	 * @param accountId 用户id
	 * @param emailId 邮箱id
	 * @return 查询到则返回邮箱信息，否则返回null
	 */
    Email getEmailById(String accountId, String emailId);
    
    /**
     * 根据邮箱地址查询邮箱信息.
     * 
     * @param email 邮箱地址
     * @return 查询到则返回true，否则返回false
     */
    boolean isEmailExisted(String email);
    
    /**
     * 根据用户id获取用户的所有邮箱信息.
     * 
     * @param accountId 用户id
     * @return 查询到则返回用户的邮箱list，否则返回长度为0的list，即返回空list
     */
    List<Email> getEmails(String accountId);
    
    /**
     * 修改邮箱信息.
     * 
     * @param isVerified 是否认证字段
     * @return 修改操作成功返回1，否则返回-1
     */
    int updateEmail(boolean isVerified);
    
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
