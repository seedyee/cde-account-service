package io.cde.account.controller;

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.domain.Account;
import io.cde.account.domain.ErrorInfo;
import io.cde.account.domain.i18n.Error;
import io.cde.account.exception.AccountNotFoundException;
import io.cde.account.exception.BizException;
import io.cde.account.service.impl.AccountServiceImpl;
import io.cde.account.tools.ErrorMessageSourceHandler;


/**
 * @author lcl
 *
 */
@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

	/**
	 * 记录日志.
	 */
	private final Logger logger = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	private ErrorMessageSourceHandler errorHandler;
	
	@Autowired
	private AccountServiceImpl accountService;
	
	/**
	 * 用户注册.
	 * 
	 * @param account 携带注册信息的用户对象
	 * @return 返回注册用户操作的结果，注册成功返回null; 失败返回导致操作失败的错误信息
	 */
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ErrorInfo createAccount(@ModelAttribute(name = "account") Account account) {
		logger.info("create account start");
		try {
			accountService.createAccount(account);
		} catch (BizException e) {
			logger.debug("create account failed, the reason " + e.getCode() + ":" + e.getMessage());
			return new ErrorInfo(e.getCode(),e.getMessage());
		}
		logger.info("create account end");
		return null;
	}
	/**
	 * 获取用户基本信息.
	 * 
	 * @param accountId 用户id
	 * @return 返回用户基本信息或是错误的操作反馈
	 */
	@RequestMapping(value = "/{accountId}/basicInfo", method = RequestMethod.GET)
	public Account getAccountInfo(@PathVariable String accountId) {
		logger.info("query account start");
		Account account = accountService.getAccountInfo(accountId);
		if (account == null) {
			AccountNotFoundException accountNotFoundException = new AccountNotFoundException(Error.INVALID_ACCOUNT_ID.getCode(), errorHandler.getMessage(Error.INVALID_ACCOUNT_ID.toString()));
			logger.debug("query account failed, the reason " + accountNotFoundException.getCode() + ":" + accountNotFoundException.getMessage());
			throw accountNotFoundException;
		}
		logger.info("query account successful");
		return account;
	}
	/**
	 * 修改用户信息.
	 * 
	 * @param accountId 用户id
	 * @param account 携带要修改的信息的用户对象
	 * @return 返回修改操作的结果，修改成功返回null；失败返回相应的错误信息
	 */
	@RequestMapping(value = "/{accountId}/basicInfo", method = RequestMethod.POST)
	public ErrorInfo updateAccountInfo(@PathVariable String accountId, @ModelAttribute(name = "account") Account account) {
		logger.info("update account information start");
		account.setId(accountId);
		try {
			accountService.updateAccount(account);
		} catch (BizException e) {
			logger.debug("update account information failed, the reason " + e.getCode() + ":" + e.getMessage());
			throw new AccountNotFoundException();
		}
		logger.info("update account infomation successful");
		return null;
	}
	
	/**
	 * 修改用户名.
	 * 
	 * @param accountId 用户id
	 * @param name 新的用户名
	 * @return 返回修改操作结果，修改成功返回null；失败返回相应的错误信息
	 */
	@RequestMapping(value = "/{accountId}/name", method = RequestMethod.POST)
	public ErrorInfo updateName(@PathVariable String accountId,
			@RequestParam(name = "name") @NotNull String name) {
		logger.info("update account name start");
		try {
			accountService.updateName(accountId, name);
		} catch (BizException e) {
			logger.debug("update account name failed, the reason " + e.getCode() + ":" + e.getMessage());
			if(e.getCode() == Error.INVALID_ACCOUNT_ID.getCode()){
				throw new AccountNotFoundException();
			}
			return new ErrorInfo(e.getCode(),e.getMessage());
		}
		logger.info("update account name successful");
		return null;
	}
	/**
	 * 修改用户密码.
	 * 
	 * @param accountId 用户id
	 * @param account 携带要修改的密码的用户对象
	 * @return 返回修改操作的结果，修改成功返回null；失败返回相应的错误信息
	 */
	@RequestMapping(value = "/{accountId}/password", method = RequestMethod.POST)
	public ErrorInfo updatePassword(@PathVariable String accountId, 
			@RequestParam(name = "password") @NotNull String password, 
			@RequestParam(name = "password1") @NotNull String password1,
			@RequestParam(name = "password2") @NotNull String password2) {
		logger.info("update account password start");
		if (!password1.equals(password2)) {
			ErrorInfo errorInfo = new ErrorInfo(Error.UNMATCHED_PASSWORD1_AND_PASSWORD2.getCode(), errorHandler.getMessage(Error.UNMATCHED_PASSWORD1_AND_PASSWORD2.toString()) );
			logger.debug("update account password failed, the reason " + errorInfo.getCode() + ":" + errorInfo.getMessage());
			return errorInfo;
		}
		try {
			accountService.updatePassword(accountId, password, password1);
		} catch (BizException e) {
			logger.debug("update account password failed, the reason " + e.getCode() + ":" + e.getMessage());
			if(e.getCode() == Error.INVALID_ACCOUNT_ID.getCode()){
				throw new AccountNotFoundException();
			}
			return new ErrorInfo(e.getCode(),e.getMessage());
		}
		logger.info("update account password successful");
		return null;
	}
}
