package io.cde.account.dao.impl;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

import io.cde.account.dao.EmailDao;
import io.cde.account.domain.Account;
import io.cde.account.domain.Email;

/**
 * @author lcl
 * @createDate 2016年11月29日下午3:56:03
 *
 */
@Repository
public class EmailDaoImpl implements EmailDao {
	
	@Autowired
    private MongoTemplate mongoTemplate;

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#getEmailById(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isAssociated(String accountId, String emailId) {
		boolean isAssociated = false;
		Query query = Query.query(Criteria.where("emails.emailId").is(emailId));
		isAssociated = mongoTemplate.exists(query, "account");
		return isAssociated;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#getEmail(java.lang.String)
	 */
	@Override
	public boolean isEmailExisted(String email) {
		boolean isEmailExisted = false;
		Query query = Query.query(Criteria.where("emails.email").is(email));
		isEmailExisted = mongoTemplate.exists(query, "account");
		return isEmailExisted;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#updateEmail(boolean)
	 */
	@Override
	public int updateEmail(String accountId, String emailId, boolean isVerified) {
		Query query = Query.query(Criteria.where("_id").is(accountId).and("emails.emailId").is(emailId));
		Update update = Update.update("emails.$.isVerified", isVerified);
		WriteResult updateFirst = mongoTemplate.updateFirst(query, update, Account.class);
		if (updateFirst.getN() <= 0) {
			return -1;
		}
		return 1;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#addEmail(java.lang.String, io.cde.account.domain.Email)
	 */
	@Override
	public int addEmail(String accountId, Email email) {
		Query query = Query.query(Criteria.where("_id").is(accountId));
		Update update = new Update();
		update.addToSet("emails", email);
		WriteResult upsert = mongoTemplate.upsert(query, update, Account.class);
		if (upsert.getN() <= 0) {
			return -1;
		}
		return 1;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.EmailDao#deleteEmail(java.lang.String, java.lang.String)
	 */
	@Override
	public int deleteEmail(String accountId, String emailId) {
		Query query = Query.query(Criteria.where("_id").is(accountId));
		BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.put("emailId", emailId);
		Update update = new Update();
		update.pull("emails", basicDBObject);
		WriteResult upsert = mongoTemplate.upsert(query, update, Account.class);
		if (upsert.getN() <= 0) {
			return -1;
		}
		return 1;
	}

}