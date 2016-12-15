package io.cde.account.tools;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.cde.account.dao.impl.AccountDaoImpl;
import io.cde.account.dao.impl.EmailDaoImpl;
import io.cde.account.dao.impl.MobileDaoImpl;
import io.cde.account.domain.Account;
import io.cde.account.domain.Email;
import io.cde.account.domain.Mobile;
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
	 * 通过用户id和邮箱id判断邮箱是否存在.
	 * 
	 * @param accountId 用户id
	 * @param emailId 邮箱id
	 * @return 
	 * @throws BizException
	 */
	public Account checkAcccountEmail(String accountId, String emailId) throws BizException {
		Account account = emailDao.findAccountByEmailId(accountId, emailId);
		if (account == null) {
			throw new BizException(Error.UNASSOCIATED_ACCOUNT_AND_EMAIL.getCode(), errorHandler.getMessage(Error.UNASSOCIATED_ACCOUNT_AND_EMAIL.toString()));
		}
		return account;
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
	public Account checkAccountMobile(String accountId, String mobileId) throws BizException {
		Account account = mobileDao.findAccountByMobileId(accountId, mobileId);
		if (account == null) {
			throw new BizException(Error.UNASSOCIATED_ACCOUNT_AND_MOBILE.getCode(), errorHandler.getMessage(Error.UNASSOCIATED_ACCOUNT_AND_MOBILE.toString()));
		}
		return account;
	}
	
	/**
	 * 根据邮箱id检测该邮箱是否是默认邮箱.
	 * 
	 * @param accountId 用户id
	 * @param emailId 邮箱id
	 * @throws BizException 
	 */
	public boolean checkDefaultEmail(String accountId, String emailId) throws BizException {
		Account account = emailDao.findAccountByEmailId(accountId, emailId);
		boolean isDefault = false;
		if (account.getEmails().size() > 0) {
			isDefault =  this.isDefaultEmail(account.getEmail(), account.getEmails(), emailId);
		}
		if (isDefault) {
			throw new BizException(Error.UNDELETABLE_DEFAULT_EMAIL.getCode(), 
					errorHandler.getMessage(Error.UNDELETABLE_DEFAULT_EMAIL.toString()));
		}
		return isDefault;
	}
	
	/**
	 * 检查默认邮箱
	 * 
	 * @param defaultEmail 默认邮箱地址
	 * @param emails 用户所有邮箱
	 * @param emailId 邮箱id
	 * @return 是默认邮箱返回true，否则返回false
	 */
	private boolean isDefaultEmail(String defaultEmail, List<Email> emails, String emailId) {
		for (Email email : emails) {
			if (emailId.equals(email.getEmailId()) && defaultEmail.equals(email.getEmail())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据电话id检测该邮箱是否是默认电话.
	 * 
	 * @param accountId 用户id
	 * @param mobileId 电话id
	 * @throws BizException 默认电话则抛出默认电话不能删除
	 */
	public boolean checkDefaultMobile(String accountId, String mobileId) throws BizException {
		Account account = mobileDao.findAccountByMobileId(accountId, mobileId);
		boolean isDefault = false;
		if (account.getMobiles().size() > 0) {
			isDefault = this.isDefaultMobile(account.getMobile(), account.getMobiles(), mobileId);
		}
		if (isDefault) {
			throw new BizException(Error.UNDELETABLE_DEFAULT_MOBILE.getCode(), 
					errorHandler.getMessage(Error.UNDELETABLE_DEFAULT_MOBILE.toString()));
		}
		return isDefault;
	}
	
	/**
	 * 检测是否是默认电话.
	 * 
	 * @param defaultMobile 默认电话号码
	 * @param mobiles 用户电话列表
	 * @param mobileId 电话id
	 * @return 是默认电话返回true
	 */
	private boolean isDefaultMobile(String defaultMobile, List<Mobile> mobiles, String mobileId) {
		for (Mobile mobile : mobiles) {
			if (mobileId.equals(mobile.getMobileId()) && mobile.getMobile().equals(defaultMobile)) {
				return true;
			}
		}
		return false;
	}
}
