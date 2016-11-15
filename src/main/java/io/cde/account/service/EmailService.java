/**
 * 
 */
package io.cde.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cde.account.dao.Interface.AccountRepository;
import io.cde.account.dao.Interface.EmailRepository;
import io.cde.account.domaim.Account;
import io.cde.account.domaim.Email;
import io.cde.account.tools.ResultUtils;

/**
 * @author lcl
 * @createDate 2016年11月13日下午10:12:34
 *
 */
@Service
public class EmailService {
	
	@Autowired
	private EmailRepository emailRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * 获取用户邮箱信息
	 * @param accountId
	 * @return
	 */
	public Object getEmails(String accountId) {
		List<Email> emails = new ArrayList<>();
		emails = emailRepository.findByAccountId(accountId);
		if (emails.size() > 0) {
			return ResultUtils.result(emails);
		}
		return ResultUtils.resultNullError();
	}
	/**
	 * 修改邮箱信息
	 * @param email
	 * @return
	 */
	public Object updateEmail(Email email) {
		Email formEmail = emailRepository.findById(email.getId());
		if (formEmail == null) {
			return ResultUtils.resultError(1000011, "用户没有关联该邮箱");
		}
		formEmail.setIsVerified(email.getIsVerified());
		emailRepository.save(formEmail);
		return ResultUtils.resultNullError();
	}
	/**
	 * 添加用户邮箱
	 * @param email
	 * @return
	 */
	public Object addEmail(Email email) {
		Email checkEmail = emailRepository.findByEmail(email.getEmail());
		if (checkEmail != null) {
			return ResultUtils.resultError(1000018, "该邮箱已经被使用过");
		}
		emailRepository.save(email);
		return ResultUtils.resultNullError();
	}
	/**
	 * 根据邮箱id删除邮箱信息,不能删除默认邮箱
	 * @param id
	 * @return
	 */
	public Object deleteEmail(String accountId, String id) {
		Email checkDefaultEmail = emailRepository.findById(id);
		Account account = accountRepository.findById(accountId);
		if (checkDefaultEmail == null || account == null ) {
			return ResultUtils.resultError(1000011, "用户没有关联该邮箱");
		}
		if (checkDefaultEmail.getEmail().equals(account.getEmail())) {
			return ResultUtils.resultError(1000020, "默认邮箱不能删除");
		}
		emailRepository.delete(checkDefaultEmail);
		return ResultUtils.resultNullError();
	}
	/**
	 * 根据邮箱地址判断邮箱是否存在
	 * @param emailAddress
	 * @return
	 */
	public boolean checkEmailByEmailAddress(String emailAddress) {
		Email email = null;
		email = emailRepository.findByEmail(emailAddress);
		if (email != null) {
			return true;
		}
		return false;
	}
}
