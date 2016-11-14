/**
 * 
 */
package io.cde.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.domaim.Account;
import io.cde.account.service.AccountService;
import io.cde.account.tools.ResultUtils;

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
	 * 注册用户
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Object createAccount(@ModelAttribute(name = "account")Account account) {
		if (account.getName() != null && account.getPassword() != null && account.getEmail() != null) {
			this.message = accountService.createAccount(account);
			return this.message;
		}
		return "传入的参数有误";
	}
	
	/**
	 * 获取用户基本信息
	 * @param accountId
	 * @return
	 */
	@RequestMapping(value = "/{accountId}/basicInfo", method = RequestMethod.GET)
	public Object getAccountInfo(@PathVariable String accountId) {
		this.message = accountService.getAccountInfo(accountId);
		return this.message;
	}
	/**
	 * 修改用户信息---------有问题
	 * @param accountId
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/{accountId}/basicInfo", method = RequestMethod.POST)
	public Object updateAccountInfo(@PathVariable String accountId, @ModelAttribute(name = "account")Account account) {
		this.message = accountService.updateAccountInfo(account);
		return this.message;
	}
	/**
	 * 修改用户密码
	 * @param accountId
	 * @param account
	 * @return
	 */
	@RequestMapping(value = "/{accountId}/password", method = RequestMethod.POST)
	public Object updateAccountPassword(@PathVariable String accountId, @ModelAttribute(name = "account")Account account) {
		if (account.getPassword() != null && account.getNewPassword() != null) {
			account.setId(accountId);
			this.message = accountService.updateAccountPassword(account);
			return message;
		}
		return ResultUtils.resultError(1000016, "没有传递参数");
	}
}
