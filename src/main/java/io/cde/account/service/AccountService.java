package io.cde.account.service;

import javax.security.auth.login.AccountNotFoundException;

import io.cde.account.domain.Account;
import io.cde.account.exception.BizException;

/**
 * @author lcl
 * @createDate 2016年11月29日下午6:07:44
 *
 */

public interface AccountService {
    
	/**
	 * 注册用户.
	 * 
	 * @param account 要注册的用户信息
	 * @return 注册成功返回1,否则返回-1
	 * @throws BizException 有户名被使用过抛出用户已存在异常
	 */
	void createAccount(Account account) throws BizException;
	
	/**
	 * 获取用户信息.
	 * 
	 * @param accountId 用户id
	 * @return 返回用户信息或抛出操作异常
	 */
	Account getAccountInfo(String accountId);
	
	/**
	 * 修改用户基本信息.
	 * 
	 * @param account 携带用户基本信息的对象
	 * @return 返回操作结果
	 * @throws BizException 
	 */
	void updateAccount(Account account) throws BizException;
	
	/**
	 * 修改用户名.
	 * 
	 * @param accountId 要修改的用户的id
	 * @param name 要修改的用户名
	 * @return 返回操作的结果
	 * @throws BizException 用户id错误抛出无效用户id异常，用户名被使用过抛出用户已存在异常
	 * @throws AccountNotFoundException 
	 */
	void updateName(String accountId,String name) throws BizException;
	
	/**
	 * 修改密码.
	 * 
	 * @param accountId 用户id
	 * @param oldPassword 用户原始密码
	 * @param newPassword 用户新密码
	 * @return 返回操作的结果
	 * @throws BizException 密码错误则抛出用户名和密码不匹配异常.
	 * @throws AccountNotFoundException 错误的用户id则抛出无效的用户id异常；
	 */
	void updatePassword(String accountId, String oldPassword, String newPassword) throws BizException;
}
