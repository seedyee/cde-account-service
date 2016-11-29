package io.cde.account.dao;


import io.cde.account.domain.Account;

/**
 * @author lcl
 * @createDate 2016年11月29日上午11:05:35
 *
 */
public interface AccountDao {
    /**
     * 创建用户.
     * @param account
     * @return 返回创建用户操作的结果，创建成功返回1，否则返回-1
     */
    int createAccount(Account account);
	
    /**
     * 根据id获取用户信息
     * @param id
     * @return 查询成功返回查询到的用户信息，否则返回null
     */
    Account findById(String id);
    
    /**
     * 根据用户名获取用户信息.
     * @param name
     * @return 查询成功返回查询到的用户信息，否则返回null
     */
    Account findByName(String name);
    
    /**
     * 修改用户信息.
     * @param account
     * @return 返回修改操作的结果，修改成功返回1，否则返回-1
     */
    int updateAccount(Account account);
    
    /**
     * 修改用户名
     * @param accountId 用户id
     * @param name 需要修改的用户名 
     * @return 返回修改操作的结果，修改成功返回1，否则返回-1
     */
    int updateName(String accountId, String name);
    /**
     * 修改用户密码.
     * @param accountId 用户id
     * @param password 需要修改的密码
     * @return 返回修改操作的结果，修改成功返回1，否则返回-1
     */
    int updatePassword(String accountId, String password);
}
