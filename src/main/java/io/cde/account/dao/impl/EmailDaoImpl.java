package io.cde.account.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import io.cde.account.dao.EmailDao;
import io.cde.account.domain.Email;

/**
 * @author lcl
 * @createDate 2016年11月29日下午3:56:03
 *
 */
public class EmailDaoImpl implements EmailDao {
	
	@Autowired
    private MongoTemplate mongoTemplate;

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#getEmailById(java.lang.String, java.lang.String)
	 */
	@Override
	public Email getEmailById(String accountId, String emailId) {
		Email email = null;
		Query query = Query.query(Criteria.where("_id").is(accountId).and("emails._id").is(emailId));
		try {
			email = mongoTemplate.findOne(query, Email.class);
		} catch (Exception e) {
			return email;
		}
		return email;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#getEmail(java.lang.String)
	 */
	@Override
	public boolean isEmailExisted(String email) {
		boolean isEmailExisted = false;
		Query query = Query.query(Criteria.where("emails.email").is(email));
		try {
			isEmailExisted = mongoTemplate.exists(query, "account");
			if (isEmailExisted) {
				return true;
			}
		} catch (Exception e) {
			return false;
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#getEmails(java.lang.String)
	 */
	@Override
	public List<Email> getEmails(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#updateEmail(boolean)
	 */
	@Override
	public int updateEmail(boolean isVerified) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#addEmail(java.lang.String, io.cde.account.domain.Email)
	 */
	@Override
	public int addEmail(String accountId, Email email) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#deleteEmail(java.lang.String, java.lang.String)
	 */
	@Override
	public int deleteEmail(String accountId, String emailId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
