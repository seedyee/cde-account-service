package io.cde.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import io.cde.account.dao.impl.AccountDaoImpl;
import io.cde.account.domain.Account;
import io.cde.account.domain.Email;
import io.cde.account.domain.i18n.Error;
import io.cde.account.domain.i18n.SystemError;
import io.cde.account.exception.BizException;
import io.cde.account.service.AccountService;
import io.cde.account.tools.AccountCheck;
import io.cde.account.tools.ErrorMessageSourceHandler;


/**
 * @author lcl
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
	
	/* (non-Javadoc)
	 * @see io.cde.account.service.impl.AccountService#createAccount(io.cde.account.domain.Account)
	 */
	@Override
	public void createAccount(Account account) throws BizException {
		Email email = new Email();
		String hashed = null;
		List<Email> emails = new ArrayList<>();
        accountCheck.checkAccountExistedByName(account.getName());
        accountCheck.checkEmailExistedByEmail(account.getEmail());
        email.setEmailId(new ObjectId().toString());
        email.setEmail(account.getEmail());
        emails.add(email);
        account.setEmails(emails);
        hashed = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
        account.setPassword(hashed);
        account.setTimestamp(new Date());
        int createAccount = accountDao.createAccount(account);
        if (createAccount <= 0) {
        		throw new BizException(SystemError.INSERT_FAILED.getCode(), errorHandler.getMessage(SystemError.INSERT_FAILED.toString()));
		}
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.impl.AccountService#getAccountInfo(java.lang.String)
	 */
	@Override
	public Account getAccountInfo(String accountId) {
		return accountDao.findById(accountId);
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.impl.AccountService#updateAccount(io.cde.account.domain.Account)
	 */
	@Override
	public void updateAccount(Account account) throws BizException{
        accountCheck.checkAccountExistedById(account.getId());
        int updateAccount = accountDao.updateAccount(account);
        if (updateAccount <= 0) {
        		throw new BizException(SystemError.UPDATE_FAILED.getCode(), errorHandler.getMessage(SystemError.UPDATE_FAILED.toString()));
		}
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.impl.AccountService#updateName(java.lang.String, java.lang.String)
	 */
	@Override
	public void updateName(String accountId, String name) throws BizException{
		accountCheck.checkAccountExistedById(accountId);
		accountCheck.checkAccountExistedByName(name);
		int updateResult = accountDao.updateName(accountId, name);
		if (updateResult <= 0) {
			throw new BizException(SystemError.UPDATE_FAILED.getCode(), errorHandler.getMessage(SystemError.UPDATE_FAILED.toString()));
		}
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.impl.AccountService#updatePassword(java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public void updatePassword(String accountId, String oldPassword, String newPassword) throws BizException{
		Account account = accountCheck.checkAccountExistedById(accountId);
		if (!BCrypt.checkpw(oldPassword, account.getPassword())) {
			throw new BizException(Error.UNMATCHED_ACCOUNT_AND_PASSWORD.getCode(), errorHandler.getMessage(Error.UNMATCHED_ACCOUNT_AND_PASSWORD.toString()));
		}
		int updatePassword = accountDao.updatePassword(accountId, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
		if (updatePassword <= 0) {
			throw new BizException(SystemError.UPDATE_FAILED.getCode(), errorHandler.getMessage(SystemError.UPDATE_FAILED.toString()));
		}
	}
}
