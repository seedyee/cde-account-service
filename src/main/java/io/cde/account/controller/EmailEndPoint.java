/**
 * 
 */
package io.cde.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.domaim.Email;
import io.cde.account.service.EmailService;

/**
 * @author lcl
 * @createDate 2016年11月13日下午10:15:00
 *
 */
@RestController
@RequestMapping(value = "/accounts")
public class EmailEndPoint {
	@Autowired
	private EmailService emailService;
	
	private Object message = null;
	/**
	 * 获取用户的邮箱信息
	 * @param accountId
	 * @return
	 */
	@RequestMapping(value = "/{accountId}/emails", method = RequestMethod.GET)
	public Object getEmails(@PathVariable String accountId) {
		message = emailService.getEmails(accountId);
		return message;
	}
	/**
	 * 修改用户的邮箱信息
	 * @param accountId
	 * @param emailId
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/{accountId}/emails/{emailId}", method = RequestMethod.POST)
	public Object updateEmail(@PathVariable String accountId, @PathVariable String emailId, @ModelAttribute(name = "email") Email email) {
		email.setId(emailId);
		message = emailService.updateEmail(email);
		return message;
	}
	/**
	 * 添加用户邮箱信息
	 * @param accountId
	 * @param email
	 * @return
	 */
	@RequestMapping(value = "/{accountId}/emails", method = RequestMethod.POST)
	public Object addEmail(@PathVariable String accountId, @ModelAttribute(name = "email") Email email) {
		message = emailService.addEmail(email);
		return message;
	}
	/**
	 * 删除用户邮箱信息
	 * @param accountId
	 * @param emailId
	 * @return
	 */
	@RequestMapping(value = "/{accountId}/emails/{emailId}", method = RequestMethod.DELETE)
	public Object deleteEmail(@PathVariable String accountId, @PathVariable String emailId) {
		message = emailService.deleteEmail(accountId,emailId);
		return message;
	}
}
