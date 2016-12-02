package io.cde.account.controller;

import javax.security.auth.login.AccountNotFoundException;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.domain.Account;
import io.cde.account.domain.ErrorInfo;
import io.cde.account.exception.AccountNotFundException;
import io.cde.account.exception.BizException;
import io.cde.account.service.impl.AccountServiceImpl;


/**
 * @author lcl
 * @createDate 2016年11月30日下午8:15:31
 *
 */
@RestController
@RequestMapping(value = "/accounts")
public class AccountController {
	@Autowired
	private AccountServiceImpl accountService;
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public Object createAccount(@ModelAttribute(name = "account") Account account) {
		try {
			accountService.createAccount(account);
		} catch (BizException e) {
			//加入日志服务以后用户日志记录异常
			e.printStackTrace();
			return new ErrorInfo(e.getCode(),e.getMessage());
		}
		return null;
	}
	/**
	 * 获取用户基本信息.
	 * @param accountId 用户id
	 * @return 返回用户基本信息或是错误的操作反馈
	 */
	@RequestMapping(value = "/{accountId}/basicInfo", method = RequestMethod.GET)
	public Account getAccountInfo(@PathVariable String accountId) {
		Account account = accountService.getAccountInfo(accountId);
		if (account == null) {
			throw new AccountNotFundException();
		}
		return account;
	}
	/**
	 * 修改用户信息
	 * @param accountId 用户id
	 * @param account 携带要修改的信息的用户对象
	 * @return 返回修改操作的结果
	 * @throws AccountNotFoundException 
	 */
	@RequestMapping(value = "/{accountId}/basicInfo", method = RequestMethod.POST)
	public Object updateAccountInfo(@PathVariable String accountId, @ModelAttribute(name = "account") Account account) {
		account.setId(accountId);
		try {
			accountService.updateAccount(account);
		} catch (BizException e) {
			throw new AccountNotFundException();
		}
		return null;
	}
	
	/**
	 * 
	 * @param accountId
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/{accountId}/name", method = RequestMethod.POST)
	public ErrorInfo updateName(@PathVariable String accountId,
			@RequestParam(name = "name") @NotNull String name) {
		try {
			accountService.updateName(accountId, name);
		} catch (BizException e) {
			if(e.getCode() == 100001){
				throw new AccountNotFundException();
			}
			return new ErrorInfo(e.getCode(),e.getMessage());
		}
		return null;
	}
	/**
	 * 修改用户密码
	 * @param accountId 用户id
	 * @param account 携带要修改的密码的用户对象
	 * @return 返回修改操作的结果
	 */
	@RequestMapping(value = "/{accountId}/password", method = RequestMethod.POST)
	public ErrorInfo updatePassword(@PathVariable String accountId, 
			@RequestParam(name = "password") @NotNull String password, 
			@RequestParam(name = "password1") @NotNull String password1,
			@RequestParam(name = "password2") @NotNull String password2) {
		if (!password1.equals(password2)) {
			return new ErrorInfo(100, "两次密码不一致");
		}
		try {
			accountService.updatePassword(accountId, password, password1);
		} catch (BizException e) {
			if(e.getCode() == 100001){
				throw new AccountNotFundException();
			}
			return new ErrorInfo(e.getCode(),e.getMessage());
		}
		return null;
	}
}
