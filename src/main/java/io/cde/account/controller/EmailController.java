package io.cde.account.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.domain.Email;
import io.cde.account.domain.ErrorInfo;
import io.cde.account.domain.i18n.Error;
import io.cde.account.exception.AccountNotFoundException;
import io.cde.account.exception.BizException;
import io.cde.account.service.impl.EmailServiceImpl;
import io.cde.account.tools.ErrorMessageSourceHandler;
import io.cde.account.tools.RegexUtils;

/**
 * @author lcl
 *
 */
@RestController
@RequestMapping(value = "/accounts")
public class EmailController {
	
	/**
	 * 记录日志.
	 */
	private final Logger logger = LoggerFactory.getLogger(EmailController.class);
    
	@Autowired
	private ErrorMessageSourceHandler errorHandler;
	
	@Autowired
	private EmailServiceImpl emailService;
	
	/**
	 * 获取用户的邮箱信息.
	 * 
	 * @param accountId 用户id
	 * @return 返回用户的邮箱信息或是错误的操作信息
	 */
	@RequestMapping(value = "/{accountId}/emails", method = RequestMethod.GET)
	public List<Email> getEmails(@PathVariable String accountId) {
	    logger.info("get the account's emails started");
		List<Email> emails = new ArrayList<>();
	    try {
	    	emails = emailService.getEmails(accountId);
		} catch (BizException e) {
			logger.debug("get the account's emails failed", e);
			throw new AccountNotFoundException();
		}
		return emails;
	}
	
	/**
	 * 修改用户的邮箱信息.
	 * 
	 * @param accountId 用户id
	 * @param emailId 要修改的邮箱id
	 * @param email 携带要修改的邮箱信息的对象
	 * @return 返回修改操作的结果
	 */
	@RequestMapping(value = "/{accountId}/emails/{emailId}", method = RequestMethod.POST)
	public ErrorInfo updateEmail(@PathVariable String accountId, @PathVariable String emailId, @RequestBody Map<String, Object> params) {
		logger.info("update email started");
		try {
			emailService.updateEmail(accountId, emailId, (Boolean) params.get("verified"));
		} catch (BizException e) {
			logger.debug("update email failed", e);
			return this.handException(e);
		}
		return new ErrorInfo();
	}
	
	/**
	 * 添加用户邮箱信息.
	 * 
	 * @param accountId 用户id
	 * @param email 携带要添加的邮箱信息的对象
	 * @return 返回添加操作的结果
	 */
	@RequestMapping(value = "/{accountId}/emails", method = RequestMethod.POST)
	public ErrorInfo addEmail(@PathVariable String accountId, @RequestBody Map<String, String> params) {
		if (!RegexUtils.isEmail(params.get("email"))) {
			return new ErrorInfo(Error.ILLEGAL_EMAIL.getCode(), errorHandler.getMessage(Error.ILLEGAL_EMAIL.toString()));
		}
		logger.info("add email started");
		Email email = new Email();
		email.setEmail(params.get("email"));
		try {
			emailService.addEmail(accountId, email);
		} catch (BizException e) {
			logger.debug("add email failed", e);
			return this.handException(e);
		}
		return new ErrorInfo();
	}
	
	/**
	 * 删除用户邮箱信息.
	 * 
	 * @param accountId 用户id
	 * @param emailId 要删除的邮箱的id
	 * @return 返回删除操作的结果
	 */
	@RequestMapping(value = "/{accountId}/emails/{emailId}", method = RequestMethod.DELETE)
	public ErrorInfo deleteEmail(@PathVariable String accountId, @PathVariable String emailId) {
		logger.info("delete email started");
		try {
			emailService.deleteEmail(accountId, emailId);
		} catch (BizException e) {
			logger.debug("delete email failed", e);
			return this.handException(e);
		}
		return new ErrorInfo();
	}
	
	/**
	 * 异常处理
	 * @param e catch到的异常
	 * @return 若是AccountNotFundException则抛出，否则返回异常对象信息
	 */
	private ErrorInfo handException(BizException e) {
		if (e.getCode() == Error.INVALID_ACCOUNT_ID.getCode()) {
			throw new AccountNotFoundException();
		}
		return new ErrorInfo(e.getCode(), e.getMessage());
	}
}
