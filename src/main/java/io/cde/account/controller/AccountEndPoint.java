package io.cde.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.domain.Account;
import io.cde.account.service.AccountService;

/**
 * @author lcl
 * @createDate 2016年11月13日下午10:14:36
 *
 */
@RestController
@RequestMapping(value = "/accounts")
public class AccountEndPoint {
	@Autowired
	private AccountService accountService;
	
	private Object message = null;
	
	/**
	 * 注册用户.
	 * @param account 注册的用户信息对象
	 * @return 返回注册操作结果
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Object createAccount(@ModelAttribute(name = "account")Account account) {
		this.message = accountService.createAccount(account);
		return this.message;
	}
	/**
	 * 获取用户基本信息.
	 * @param accountId 用户id
	 * @return 返回用户基本信息或是错误的操作反馈
	 */
	@RequestMapping(value = "/{accountId}/basicInfo", method = RequestMethod.GET)
	public Object getAccountInfo(@PathVariable String accountId) {
		this.message = accountService.getAccountInfo(accountId);
		return this.message;
	}
	/**
	 * 修改用户信息
	 * @param accountId 用户id
	 * @param account 携带要修改的信息的用户对象
	 * @return 返回修改操作的结果
	 */
	@RequestMapping(value = "/{accountId}/basicInfo", method = RequestMethod.POST)
	public Object updateAccountInfo(@PathVariable String accountId, @ModelAttribute(name = "account")Account account) {
		this.message = accountService.updateAccountInfo(account);
		return this.message;
	}
	/**
	 * 修改用户密码
	 * @param accountId 用户id
	 * @param account 携带要修改的密码的用户对象
	 * @return 返回修改操作的结果
	 */
	@RequestMapping(value = "/{accountId}/password", method = RequestMethod.POST)
	public Object updateAccountPassword(@PathVariable String accountId, @ModelAttribute(name = "account")Account account) {
		account.setId(accountId);
		this.message = accountService.updateAccountPassword(account);
		return message;
	}
}
