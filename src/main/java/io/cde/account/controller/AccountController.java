package io.cde.account.controller;

import java.util.Map;

import javax.servlet.ServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.domain.Account;
import io.cde.account.domain.ErrorInfo;
import io.cde.account.domain.i18n.Error;
import io.cde.account.exception.AccountNotFoundException;
import io.cde.account.exception.BizException;
import io.cde.account.service.impl.AccountServiceImpl;
import io.cde.account.tools.ErrorMessageSourceHandler;
import io.cde.account.tools.RegexUtils;


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
	@RequestMapping(method = RequestMethod.POST)
	public ErrorInfo createAccount(@RequestBody Map<String, String> account, ServletRequest request) {
		Account accounts = new Account();
		if(account.get("name") == null || account.get("password") == null || account.get("email") == null) {
			return new ErrorInfo(Error.MISS_REQUIRED_PARAMETER.getCode(), errorHandler.getMessage(Error.MISS_REQUIRED_PARAMETER.toString()));
		}
		if (!RegexUtils.isAccountName(account.get("name"))) {
			return new ErrorInfo(Error.ILLEGAL_ACCOUNT_NAME.getCode(), errorHandler.getMessage(Error.ILLEGAL_ACCOUNT_NAME.toString()));
		}
		if (!RegexUtils.isAccountPassword(account.get("password"))) {
			return new ErrorInfo(Error.ILLEGAL_PASSWORD.getCode(), errorHandler.getMessage(Error.ILLEGAL_PASSWORD.toString()));
		}
		if (!RegexUtils.isEmail(account.get("email"))) {
			return new ErrorInfo(Error.ILLEGAL_EMAIL.getCode(), errorHandler.getMessage(Error.ILLEGAL_EMAIL.toString()));
		}
		accounts.setName(account.get("name"));
		accounts.setEmail(account.get("email"));
		accounts.setPassword(account.get("password"));
		logger.info("create account start");
		try {
			accountService.createAccount(accounts);
		} catch (BizException e) {
			logger.debug("create account failed, the reason ", e);
			return new ErrorInfo(e.getCode(),e.getMessage());
		}
		return new ErrorInfo();
	}
	/**
	 * 获取用户基本信息.
	 * 
	 * @param accountId 用户id
	 * @return 返回用户基本信息或是错误的操作反馈
	 */
	@RequestMapping(value = "/{accountId}/basicInfo", method = RequestMethod.GET)
	public Account getAccountInfo(@PathVariable String accountId) {
		logger.info("query account started");
		Account account = accountService.getAccountInfo(accountId);
		if (account == null) {
			AccountNotFoundException accountNotFoundException = new AccountNotFoundException(Error.INVALID_ACCOUNT_ID.getCode(), errorHandler.getMessage(Error.INVALID_ACCOUNT_ID.toString()));
			logger.debug("query account failed", accountNotFoundException);
			throw accountNotFoundException;
		}
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
	public ErrorInfo updateAccountInfo(@PathVariable String accountId, @RequestBody Account account) {
		logger.info("update account information started");
		account.setId(accountId);
		try {
			accountService.updateAccount(account);
		} catch (BizException e) {
			logger.debug("update account information failed", e);
			throw new AccountNotFoundException();
		}
		return new ErrorInfo();
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
			@RequestBody Map<String, String> params) {
		if (params.get("name") == null) {
			return new ErrorInfo(Error.MISS_REQUIRED_PARAMETER.getCode(), errorHandler.getMessage(Error.MISS_REQUIRED_PARAMETER.toString()));
		}
		if (!RegexUtils.isAccountName(params.get("name"))) {
			return new ErrorInfo(Error.ILLEGAL_ACCOUNT_NAME.getCode(), errorHandler.getMessage(Error.ILLEGAL_ACCOUNT_NAME.toString()));
		}
		logger.info("update account name started");
		try {
			accountService.updateName(accountId, params.get("name"));
		} catch (BizException e) {
			logger.debug("update account name failed", e);
			if(e.getCode() == Error.INVALID_ACCOUNT_ID.getCode()){
				throw new AccountNotFoundException();
			}
			return new ErrorInfo(e.getCode(),e.getMessage());
		}
		return new ErrorInfo();
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
			@RequestBody Map<String, String> params ) {
		logger.info("update account password started");
		if (params.get("password1") == null || params.get("password2") == null || params.get("password") == null) {
			return new ErrorInfo(Error.MISS_REQUIRED_PARAMETER.getCode(), errorHandler.getMessage(Error.MISS_REQUIRED_PARAMETER.toString()));
		}
		if (!params.get("password1").equals(params.get("password2"))) {
			ErrorInfo errorInfo = new ErrorInfo(Error.UNMATCHED_PASSWORD1_AND_PASSWORD2.getCode(), errorHandler.getMessage(Error.UNMATCHED_PASSWORD1_AND_PASSWORD2.toString()) );
			return errorInfo;
		}
		if (!RegexUtils.isAccountPassword(params.get("password1"))) {
			return new ErrorInfo(Error.ILLEGAL_PASSWORD.getCode(), errorHandler.getMessage(Error.ILLEGAL_PASSWORD.toString()));
		}
		try {
			accountService.updatePassword(accountId, params.get("password"), params.get("password1"));
		} catch (BizException e) {
			logger.debug("update account password failed", e);
			if(e.getCode() == Error.INVALID_ACCOUNT_ID.getCode()){
				throw new AccountNotFoundException();
			}
			return new ErrorInfo(e.getCode(),e.getMessage());
		}
		return new ErrorInfo();
	}
}
