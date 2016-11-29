package io.cde.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cde.account.dao.Interface.AccountRepository;
import io.cde.account.dao.Interface.EmailRepository;
import io.cde.account.domaim.i18n.error.ErrorStatus;
import io.cde.account.domain.Account;
import io.cde.account.domain.Email;
import io.cde.account.tools.ErrorMessageSourceHandler;
import io.cde.account.tools.ResultUtils;

/**
 * @author lcl
 * @createDate 2016年11月13日下午10:12:34
 *
 */
@Service
public class EmailService {
	
	@Autowired
	private ErrorMessageSourceHandler errorHandler;
	/**
     * 
     */
	@Autowired
	private EmailRepository emailRepository;
	/**
     * 
     */
	@Autowired
	private AccountRepository accountRepository;

	/**
	 * 获取用户邮箱信息
	 * @param accountId 用户id
	 * @return 返回用户邮箱信息
	 */
	public Object getEmails(String accountId) {
		List<Email> emails = new ArrayList<>();
		emails = emailRepository.findByAccountId(accountId);
		if (emails.size() > 0) {
			Account account = accountRepository.findById(accountId);
			emails = this.setDefaultAndPublicEmail(emails, account.getEmail(), account.getIsPublicEmail());
			return ResultUtils.result(emails);
		}
		return ResultUtils.resultNullError();
	}

	/**
	 * 修改邮箱信息。
	 * @param email 需要修改的邮箱对象
	 * @return 返回修改操作的结果
	 */
	public Object updateEmail(Email email) {
		Email formEmail = emailRepository.findById(email.getId());
		if (formEmail == null) {
			return ResultUtils.resultError(ErrorStatus.ACCOUNT_NOT_EMAIL.getCode(), errorHandler.getMessage(ErrorStatus.ACCOUNT_NOT_EMAIL.toString()));
		}
		formEmail.setIsVerified(email.getIsVerified());
		emailRepository.save(formEmail);
		return ResultUtils.resultNullError();
	}
	/**
	* 添加用户邮箱
  	* @param email 添加的邮箱对象
	* @return 返回添加结果
	*/
	public Object addEmail(Email email) {
		Email checkEmail = emailRepository.findByEmail(email.getEmail());
		if (checkEmail != null) {
			return ResultUtils.resultError(ErrorStatus.EMAIL_EXISTED.getCode(), errorHandler.getMessage(ErrorStatus.EMAIL_EXISTED.toString()));
		}
		emailRepository.save(email);
		return ResultUtils.resultNullError();
	}
	/**
	 * 根据邮箱id删除邮箱信息,不能删除默认邮箱
	 * @param accountId 用户id
	 * @param id 邮箱id
	 * @return 返回操作的结果
	 */
	public Object deleteEmail(String accountId, String id) {
		Email checkDefaultEmail = emailRepository.findById(id);
		Account account = accountRepository.findById(accountId);
		if (checkDefaultEmail == null || account == null ) {
			return ResultUtils.resultError(ErrorStatus.ACCOUNT_NOT_EMAIL.getCode(), errorHandler.getMessage(ErrorStatus.ACCOUNT_NOT_EMAIL.toString()));
		}
		if (checkDefaultEmail.getEmail().equals(account.getEmail())) {
			return ResultUtils.resultError(ErrorStatus.ILLEGAL_DELETE_EMAIL.getCode(), errorHandler.getMessage(ErrorStatus.ILLEGAL_DELETE_EMAIL.toString()));
		}
		emailRepository.delete(checkDefaultEmail);
		return ResultUtils.resultNullError();
	}
	/**
	 * 根据邮箱地址判断邮箱是否存在
	 * @param emailAddress 邮箱地址
	 * @return 存在返回true，不存在返回false
	 */
	public boolean checkEmailByEmailAddress(String emailAddress) {
		Email email = null;
		email = emailRepository.findByEmail(emailAddress);
		if (email != null) {
			return true;
		}
		return false;
	}
	/**
	 * 封装返回数据
	 * @param emails 用户所有邮箱
	 * @param email 用户默认邮箱
	 * @param isPublicEmail 默认邮箱是否是公开
	 * @return
	 */
	private List<Email> setDefaultAndPublicEmail(List<Email> emails, String email, boolean isPublicEmail) {
		for (Email email2 : emails) {
			if (email2.getEmail().equals(email)) {
				email2.setIsDefault(true);
				email2.setIsPublic(isPublicEmail);
			}
		}
		return emails;
	}
}
