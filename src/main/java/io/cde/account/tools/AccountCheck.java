package io.cde.account.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.cde.account.dao.impl.AccountDaoImpl;
import io.cde.account.dao.impl.EmailDaoImpl;
import io.cde.account.dao.impl.MobileDaoImpl;
import io.cde.account.domain.Account;
import io.cde.account.domain.i18n.Error;
import io.cde.account.exception.BizException;

/**
 * @author lcl
 *
 */
@Component
public class AccountCheck {
	
	@Autowired
	private ErrorMessageSourceHandler errorHandler;
	
	@Autowired
    private AccountDaoImpl accountDao;
	
	@Autowired
	private EmailDaoImpl emailDao;
	
	@Autowired
	private MobileDaoImpl mobileDao;
	
	/**
	 * 根据用户名判断用户是否存在.
	 * 
	 * @param name 用户名
	 * @throws BizException 用户名存在则抛用户已存在异常
	 */
	public void checkAccountExistedByName(String name) throws BizException {
		Account account = accountDao.findByName(name);
		if (account != null) {
			throw new BizException(Error.ACCOUNT_EXISTE.getCode(), errorHandler.getMessage(Error.ACCOUNT_EXISTE.toString()));
		}
	}
	
	/**
	 * 根据用户id判断用户是否存在.
	 * 
	 * @param accountId 用户id
	 * @throws BizException 用户不存在则抛出无效用户id
	 */
	public Account checkAccountExistedById(String accountId) throws BizException {
		Account account = accountDao.findById(accountId);
		if (account == null) {
			throw new BizException(Error.INVALID_ACCOUNT_ID.getCode(), errorHandler.getMessage(Error.INVALID_ACCOUNT_ID.toString()));
		}
		return account;
	}
	
	/**
	 * 根据邮箱地址判断邮箱是够存在.
	 * 
	 * @param email 邮箱地址
	 * @throws BizException 存在则抛出邮箱已经被使用过业务异常
	 */
	public void checkEmailExistedByEmail(String email) throws BizException {
		boolean isExisted = emailDao.isEmailExisted(email);
		if (isExisted) {
			throw new BizException(Error.USED_EMAIL.getCode(), errorHandler.getMessage(Error.USED_EMAIL.toString()));
		}
	}
	
	/**
	 * 判断用户是否关联邮箱.
	 * 
	 * @param accountId 用户id
	 * @param emailId 邮箱id
	 * @throws BizException 没有关联则抛出用户没有关联该邮箱异常
	 */
	public void checkAcccountEmail(String accountId, String emailId) throws BizException {
		boolean isAssociated = emailDao.isAssociated(accountId, emailId);
		if (!isAssociated) {
			throw new BizException(Error.UNASSOCIATED_ACCOUNT_AND_EMAIL.getCode(), errorHandler.getMessage(Error.UNASSOCIATED_ACCOUNT_AND_EMAIL.toString()));
		}
	}
	
	public void checkMobileExistedByMobile(String mobile) throws BizException {
		boolean isExisted = mobileDao.isMobileExisted(mobile);
		if (isExisted) {
			throw new BizException(Error.USED_MOBILE.getCode(), errorHandler.getMessage(Error.USED_MOBILE.toString()));
		}
	}
	
	/**
	 * 判断用户是否关联该邮箱.
	 * 
	 * @param accountId 用户id
	 * @param mobileId 电话id
	 * @throws BizException 没有关联则抛出用户没有关联该电话号码异常
	 */
	public void checkAccountMobile(String accountId, String mobileId) throws BizException {
		boolean isAssociated = mobileDao.isAssociated(accountId, mobileId);
		if (!isAssociated) {
			throw new BizException(Error.UNASSOCIATED_ACCOUNT_AND_MOBILE.getCode(), errorHandler.getMessage(Error.UNASSOCIATED_ACCOUNT_AND_MOBILE.toString()));
		}
	}
}
