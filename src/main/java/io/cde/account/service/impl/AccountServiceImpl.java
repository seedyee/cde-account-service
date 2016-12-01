package io.cde.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.login.AccountNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cde.account.dao.AccountRepository;
import io.cde.account.dao.impl.AccountDaoImpl;
import io.cde.account.domain.Account;
import io.cde.account.domain.Email;
import io.cde.account.domain.i18n.Error;
import io.cde.account.exception.BizException;
import io.cde.account.tools.AccountCheck;
import io.cde.account.tools.ErrorMessageSourceHandler;


/**
 * @author lcl
 * @createDate 2016年11月30日上午9:48:55
 *
 */
@Service
public class AccountServiceImpl implements AccountService{
	
	@Autowired
	private ErrorMessageSourceHandler errorHandler;
	
	@Autowired
	private AccountCheck accountCheck;
	
	@Autowired
	private AccountDaoImpl accountDao;
	
	@Autowired
	private AccountRepository accountRepository;
	
	/* (non-Javadoc)
	 * @see io.cde.account.service.impl.AccountService#createAccount(io.cde.account.domain.Account)
	 */
	@Override
	public int createAccount(Account account) throws BizException {
		int createResult = -1;
		Email email = new Email();
		List<Email> emails = new ArrayList<>();
        accountCheck.checkAccountExistedByName(account.getName());
        accountCheck.checkEmailExistedByEmail(account.getEmail());
        email.setEmail(account.getEmail());
        emails.add(email);
        account.setEmails(emails);
        createResult = accountDao.createAccount(account);
		return createResult;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.impl.AccountService#getAccountInfo(java.lang.String)
	 */
	@Override
	public Account getAccountInfo(String accountId) {
	    //accountCheck.checkAccountExistedById(accountId);
		return accountRepository.findById(accountId);
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.impl.AccountService#updateAccount(io.cde.account.domain.Account)
	 */
	@Override
	public int updateAccount(Account account) throws BizException{
        accountCheck.checkAccountExistedById(account.getId());
        accountDao.updateAccount(account);
		return 1;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.impl.AccountService#updateName(java.lang.String, java.lang.String)
	 */
	@Override
	public int updateName(String accountId, String name) throws BizException{
		accountCheck.checkAccountExistedById(accountId);
		accountCheck.checkAccountExistedByName(name);
		accountDao.updateName(accountId, name);
		return 1;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.impl.AccountService#updatePassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public int updatePassword(String accountId, String oldPassword, String newPassword) throws BizException{
		Account account = accountCheck.checkAccountExistedById(accountId);
		if (!account.getPassword().equals(oldPassword)) {
			throw new BizException(Error.UNMATCHED_ACCOUNT_AND_PASSWORD.getCode(), errorHandler.getMessage(Error.UNMATCHED_ACCOUNT_AND_PASSWORD.toString()));
		}
		accountDao.updatePassword(accountId, newPassword);
		return 1;
	}
}
