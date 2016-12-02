package io.cde.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cde.account.dao.impl.EmailDaoImpl;
import io.cde.account.domain.Account;
import io.cde.account.domain.Email;
import io.cde.account.exception.BizException;
import io.cde.account.service.EmailService;
import io.cde.account.tools.AccountCheck;
import io.cde.account.tools.ErrorMessageSourceHandler;

/**
 * @author lcl
 *
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
	public List<Email> getEmails(String accountId) throws BizException {
		List<Email> emails = new ArrayList<>();
		Account account = accountCheck.checkAccountExistedById(accountId);
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
	public void addEmail(String accountId, Email email) throws BizException {
		accountCheck.checkAccountExistedById(accountId);
		accountCheck.checkEmailExistedByEmail(email.getEmail());
		email.setEmailId(new ObjectId().toString());
		int addEmail = emailDao.addEmail(accountId, email);
		if (addEmail <= 0) {
			throw new BizException(1, "添加邮箱失败");
		}
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.EmailService#updateEmail(java.lang.String, java.lang.String, boolean)
	 */
	@Override
	public void updateEmail(String accountId, String emailId, boolean isVerified) throws BizException {
        accountCheck.checkAcccountEmail(accountId, emailId);
        int updateEmail = emailDao.updateEmail(accountId, emailId, isVerified);
        if (updateEmail <= 0) {
			throw new BizException(1, "更新邮箱失败");
		}
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.EmailService#deleteEmail(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteEmail(String accountId, String emailId) throws BizException {
		accountCheck.checkAcccountEmail(accountId, emailId);
		int deleteEmail = emailDao.deleteEmail(accountId, emailId);
		if (deleteEmail <= 0) {
			throw new BizException(1, "删除邮箱失败");
		}
	}
	
	/**
	 * 组装邮箱数据，设置默认是否默认，是否公开.
	 * 
	 * @param emails 邮箱集合
	 * @param defaultEmail 默认邮箱地址
	 * @param isDefault 是否默认
	 * @param isPublic 是否公开
	 * @return 返回设置好的邮箱集合
	 */
    private List<Email> assembleData(List<Email> emails, String defaultEmail,boolean isPublic) {
        for (Email email : emails) {
			if (email.getEmail().equals(defaultEmail)) {
				email.setDefault(true);
				email.setPublic(isPublic);
			}
		} 
        return emails;
    }
}
