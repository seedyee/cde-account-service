package io.cde.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import io.cde.account.dao.impl.EmailDaoImpl;
import io.cde.account.domain.Account;
import io.cde.account.domain.Email;
import io.cde.account.domain.i18n.SystemError;
import io.cde.account.exception.BizException;
import io.cde.account.service.EmailService;
import io.cde.account.tools.AccountCheck;
import io.cde.account.tools.ErrorMessageSourceHandler;

/**
 * @author lcl
 */
@Service
public class EmailServiceImpl implements EmailService {

    @Autowired
    private ErrorMessageSourceHandler errorHandler;

    @Autowired
    private AccountCheck accountCheck;

    @Autowired
    private EmailDaoImpl emailDao;

    /* (non-Javadoc)
     * @see io.cde.account.service.EmailService#getEmails(java.lang.String)
     */
    @Override
    @Cacheable(cacheNames = "emails", key = "'email:' + #accountId")
    public List<Email> getEmails(final String accountId) throws BizException {
        List<Email> emails;
        final Account account = accountCheck.checkAccountExistedById(accountId);
        emails = account.getEmails();
        if (emails.size() > 0) {
            emails = this.assembleData(emails, account.getEmail(), account.isPublicEmail());
        }
        return emails;
    }

    /* (non-Javadoc)
     * @see io.cde.account.service.EmailService#addEmail(java.lang.String, io.cde.account.domain.Email)
     */
    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "accounts", key = "'account:' + #accountId"),
            @CacheEvict(cacheNames = "emails", key = "'email:' + #accountId")})
    public void addEmail(final String accountId, final Email email) throws BizException {
        accountCheck.checkAccountExistedById(accountId);
        accountCheck.checkEmailExistedByEmail(email.getEmail());
        email.setEmailId(new ObjectId().toString());
        final int addEmail = emailDao.addEmail(accountId, email);
        if (addEmail <= 0) {
            throw new BizException(SystemError.INSERT_FAILED.getCode(), errorHandler.getMessage(SystemError.INSERT_FAILED.toString()));
        }
    }

    /* (non-Javadoc)
     * @see io.cde.account.service.EmailService#updateEmail(java.lang.String, java.lang.String, boolean)
     */
    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "accounts", key = "'account:' + #accountId"),
            @CacheEvict(cacheNames = "emails", key = "'email:' + #accountId")})
    public void updateEmail(final String accountId, final String emailId, final boolean isVerified) throws BizException {
        accountCheck.checkAcccountEmail(accountId, emailId);
        final int updateEmail = emailDao.updateEmail(accountId, emailId, isVerified);
        if (updateEmail <= 0) {
            throw new BizException(SystemError.UPDATE_FAILED.getCode(), errorHandler.getMessage(SystemError.UPDATE_FAILED.toString()));
        }
    }

    /* (non-Javadoc)
     * @see io.cde.account.service.EmailService#updatePublicEmail(java.lang.String, boolean)
     */
    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "accounts", key = "'account:' + #accountId"),
            @CacheEvict(cacheNames = "emails", key = "'email:' + #accountId")})
    public void updatePublicEmail(final String accountId, final boolean isPublic) throws BizException {
        accountCheck.checkAccountExistedById(accountId);
        final int updateEmail = emailDao.updatePublicEmail(accountId, isPublic);
        if (updateEmail <= 0) {
            throw new BizException(SystemError.UPDATE_FAILED.getCode(), errorHandler.getMessage(SystemError.UPDATE_FAILED.toString()));
        }
    }

    /* (non-Javadoc)
     * @see io.cde.account.service.EmailService#deleteEmail(java.lang.String, java.lang.String)
     */
    @Override
    @Caching(evict = {@CacheEvict(cacheNames = "accounts", key = "'account:' + #accountId"),
            @CacheEvict(cacheNames = "emails", key = "'email:' + #accountId")})
    public void deleteEmail(final String accountId, final String emailId) throws BizException {
        accountCheck.checkAcccountEmail(accountId, emailId);
        accountCheck.checkDefaultEmail(accountId, emailId);
        final int deleteEmail = emailDao.deleteEmail(accountId, emailId);
        if (deleteEmail <= 0) {
            throw new BizException(SystemError.DELETE_FAILED.getCode(), errorHandler.getMessage(SystemError.DELETE_FAILED.toString()));
        }
    }

    /**
     * 组装邮箱数据，设置默认是否默认，是否公开.
     *
     * @param emails       邮箱集合
     * @param defaultEmail 默认邮箱地址
     * @param isPublic     是否公开
     * @return 返回设置好的邮箱集合
     */
    private List<Email> assembleData(final List<Email> emails, final String defaultEmail, final boolean isPublic) {
        for (Email email : emails) {
            if (email.getEmail().equals(defaultEmail)) {
                email.setDefault(true);
                email.setPublic(isPublic);
            }
        }
        return emails;
    }
}
