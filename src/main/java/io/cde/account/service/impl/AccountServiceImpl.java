package io.cde.account.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
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
 */
@Service
public class AccountServiceImpl implements AccountService {

    /**
     * ErrorMessageSourceHandler对象.
     */
    private ErrorMessageSourceHandler errorHandler;

    /**
     * AccountCheck对象.
     */
    private AccountCheck accountCheck;

    /**
     * AccountDaoImpl对象.
     */
    private AccountDaoImpl accountDao;

    /**
     * 通过构造器注入对象.
     *
     * @param errorHandler errorHandler对象
     * @param accountCheck accountCheck对象
     * @param accountDao accountDao对象
     */
    @Autowired
    public AccountServiceImpl(final ErrorMessageSourceHandler errorHandler, final AccountCheck accountCheck, final AccountDaoImpl accountDao) {
        this.errorHandler = errorHandler;
        this.accountCheck = accountCheck;
        this.accountDao = accountDao;
    }

    /* (non-Javadoc)
     * @see io.cde.account.service.impl.AccountService#createAccount(io.cde.account.domain.Account)
     */
    @Override
    public void createAccount(final Account account) throws BizException {
        final Email email = new Email();
        final String hashed;
        final List<Email> emails = new ArrayList<>();
        accountCheck.checkAccountExistedByName(account.getName());
        accountCheck.checkEmailExistedByEmail(account.getEmail());
        email.setEmailId(new ObjectId().toString());
        email.setEmail(account.getEmail());
        emails.add(email);
        account.setEmails(emails);
        hashed = BCrypt.hashpw(account.getPassword(), BCrypt.gensalt());
        account.setPassword(hashed);
        account.setTimestamp(new Date());
        final int createAccount = accountDao.createAccount(account);
        if (createAccount <= 0) {
            throw new BizException(SystemError.INSERT_FAILED.getCode(), errorHandler.getMessage(SystemError.INSERT_FAILED.toString()));
        }
    }

    /* (non-Javadoc)
     * @see io.cde.account.service.impl.AccountService#getAccountInfo(java.lang.String)
     */
    @Override
    @Cacheable(cacheNames = {"accounts"}, key = "'account:' + #accountId")
    public Account getAccountInfo(final String accountId) {
        return accountDao.findById(accountId);
    }

    /* (non-Javadoc)
     * @see io.cde.account.service.impl.AccountService#updateAccount(io.cde.account.domain.Account)
     */
    @Override

    @Caching(evict = {@CacheEvict(cacheNames = "accounts", key = "'account:' + #account.id"),
            @CacheEvict(cacheNames = "emails", key = "'email:' + #account.id"),
            @CacheEvict(cacheNames = "mobiles", key = "'mobile:' + #account.id")})
    public void updateAccount(final Account account) throws BizException {
        accountCheck.checkAccountExistedById(account.getId());
        final int updateAccount = accountDao.updateAccount(account);
        if (updateAccount <= 0) {
            throw new BizException(SystemError.UPDATE_FAILED.getCode(), errorHandler.getMessage(SystemError.UPDATE_FAILED.toString()));
        }
    }

    /* (non-Javadoc)
     * @see io.cde.account.service.impl.AccountService#updateName(java.lang.String, java.lang.String)
     */
    @Override
    @CacheEvict(cacheNames = {"accounts"}, key = "'account:' + #accountId")
    public void updateName(final String accountId, final String name) throws BizException {
        accountCheck.checkAccountExistedById(accountId);
        accountCheck.checkAccountExistedByName(name);
        final int updateResult = accountDao.updateName(accountId, name);
        if (updateResult <= 0) {
            throw new BizException(SystemError.UPDATE_FAILED.getCode(), errorHandler.getMessage(SystemError.UPDATE_FAILED.toString()));
        }
    }

    /* (non-Javadoc)
     * @see io.cde.account.service.impl.AccountService#updatePassword(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    @CacheEvict(cacheNames = {"accounts"}, key = "'account:' + #accountId")
    public void updatePassword(final String accountId, final String oldPassword, final String newPassword) throws BizException {
        final Account account = accountCheck.checkAccountExistedById(accountId);
        if (!BCrypt.checkpw(oldPassword, account.getPassword())) {
            throw new BizException(Error.UNMATCHED_ACCOUNT_AND_PASSWORD.getCode(), errorHandler.getMessage(Error.UNMATCHED_ACCOUNT_AND_PASSWORD.toString()));
        }
        final int updatePassword = accountDao.updatePassword(accountId, BCrypt.hashpw(newPassword, BCrypt.gensalt()));
        if (updatePassword <= 0) {
            throw new BizException(SystemError.UPDATE_FAILED.getCode(), errorHandler.getMessage(SystemError.UPDATE_FAILED.toString()));
        }
    }
}
