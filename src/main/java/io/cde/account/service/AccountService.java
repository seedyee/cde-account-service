package io.cde.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import io.cde.account.dao.Interface.AccountRepository;
import io.cde.account.dao.Interface.EmailRepository;
import io.cde.account.domaim.Account;
import io.cde.account.domaim.Email;
import io.cde.account.domaim.i18n.error.ErrorStatus;
import io.cde.account.tools.ErrorMessageSourceHandler;
import io.cde.account.tools.MergeObjectUtils;
import io.cde.account.tools.ResultUtils;

/**
 * @author lcl
 * @createDate 2016年11月13日下午10:12:18
 * 
 */
@Service
public class AccountService {
    
	@Autowired
	private ErrorMessageSourceHandler errorHandler;
    
	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private EmailRepository emailRepository;

	@Autowired
	private EmailService emailService;

	/**
	 * 注册用户
	 * @param account 携带用户信息的对象
	 * @return 返回注册结果
	 */
	public Object createAccount(Account account) {
		Account createAccount = null;
		String hashed = null;
		Email email = new Email();
		boolean checkAccount = this.checkAccountByName(account.getName());
		boolean checkEmail = emailService.checkEmailByEmailAddress(account.getEmail());
		
		if (checkEmail == true || checkAccount == true) {
			return ResultUtils.resultError(ErrorStatus.ACCOUNT_EXISTED.getCode(), errorHandler.getMessage(ErrorStatus.ACCOUNT_EXISTED.toString()));
		}
		//加密存入数据库
		hashed = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
		account.setPassword(hashed);
		createAccount = accountRepository.save(account);
		email.setAccountId(createAccount.getId());
		email.setEmail(account.getEmail());
		email.setIsVerified(false);
		emailRepository.save(email);
		return ResultUtils.resultNullError();
	}
	/**
	 * 获取用户信息
	 * @param accountId 用户id
	 * @return 返回获取用户信息的操作结果
	 */
	public Object getAccountInfo(String accountId) {
		Account account = null;
		account = accountRepository.findById(accountId);
		return ResultUtils.result(account);
	} 
	/**
	 * 修改用户信息
	 * @param account 携带修改信息的用户对象
	 * @return 返回修改操作的结果
	 */
	public Object updateAccountInfo(Account account) {
		Account formAccount = accountRepository.findById(account.getId());
		if (formAccount == null) {
			return ResultUtils.resultNullError();
		}
		if (account.getName() != null) {
			boolean checkAccount = this.checkAccountByName(account.getName());
			if (checkAccount) {
				return ResultUtils.resultError(ErrorStatus.ACCOUNT_EXISTED.getCode(), errorHandler.getMessage(ErrorStatus.ACCOUNT_EXISTED.toString()));
			}
			formAccount.setName(account.getName());
		}
		formAccount = (Account) MergeObjectUtils.merge(formAccount, account);
		accountRepository.save(formAccount);
		return ResultUtils.resultNullError();
	}
	/**
	 * 修改用户密码
	 * @param account 需要修改密码的用户
	 * @return 返回修改操作的结果
	 */
	public Object updateAccountPassword(Account account) {
		Account formAccount = accountRepository.findById(account.getId());
		//检查密码是否正确
		if (BCrypt.checkpw(account.getPassword(), formAccount.getPassword())) {
			formAccount.setPassword(BCrypt.hashpw(account.getNewPassword(), BCrypt.gensalt()));
			accountRepository.save(formAccount);
			return ResultUtils.resultNullError();
		}
		return ResultUtils.resultError(ErrorStatus.ILLEGAL_ACCOUNT_PASSWORD.getCode(), errorHandler.getMessage(ErrorStatus.ILLEGAL_ACCOUNT_PASSWORD.toString()));
	}
	
	/**
	 * 根据用户名判断用户是否存在
	 * @param name 用户名
	 * @return 若存在返回true，不存在返回false
	 */
	private boolean checkAccountByName(String name) {
		Account account = null;
		account = accountRepository.findByName(name);
		if (account != null) {
			return true;
		}
		return false;
	}
}
