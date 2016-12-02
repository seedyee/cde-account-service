package io.cde.account.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.BasicDBObject;
import com.mongodb.WriteResult;

import io.cde.account.dao.MobileDao;
import io.cde.account.domain.Account;
import io.cde.account.domain.Mobile;

/**
 * @author lcl
 * @createDate 2016年11月29日下午5:55:10
 *
 */
@Repository
public class MobileDaoImpl implements MobileDao {
    
	@Autowired
	private MongoTemplate mongoTemplate;
	
	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#getMobileById(java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isAssociated(String accountId, String mobileId) {
		boolean isAssociated = false;
		Query query = Query.query(Criteria.where("mobiles.mobileId").is(mobileId));
		isAssociated = mongoTemplate.exists(query, "account");
		return isAssociated;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#isMobileExisted(java.lang.String)
	 */
	@Override
	public boolean isMobileExisted(String mobile) {
		boolean isEmailExisted = false;
		Query query = Query.query(Criteria.where("mobiles.mobile").is(mobile));
		isEmailExisted = mongoTemplate.exists(query, "account");
		return isEmailExisted;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#updateMobile(java.lang.String, io.cde.account.domain.Mobile)
	 */
	@Override
	public int updateMobile(String accountId, String mobileId, boolean isVerified) {
		Query query = Query.query(Criteria.where("_id").is(accountId).and("mobiles.mobileId").is(mobileId));
		Update update = Update.update("mobiles.$.isVerified", isVerified);
		WriteResult updateFirst = mongoTemplate.updateFirst(query, update, Account.class);
		if (updateFirst.getN() <= 0) {
			return -1;
		}
		return 1;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#addMobile(java.lang.String, io.cde.account.domain.Mobile)
	 */
	@Override
	public int addMobile(String accountId, Mobile mobile) {
		Query query = Query.query(Criteria.where("_id").is(accountId));
		Update update = new Update();
		update.addToSet("mobiles", mobile);
		WriteResult upsert = mongoTemplate.upsert(query, update, Account.class);
		if (upsert.getN() <= 0) {
			return -1;
		}
		return 1;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#deleteMobile(java.lang.String, java.lang.String)
	 */
	@Override
	public int deleteMobile(String accountId, String mobileId) {
		Query query = Query.query(Criteria.where("_id").is(accountId));
		BasicDBObject basicDBObject = new BasicDBObject();
		basicDBObject.put("mobileId", mobileId);
		Update update = new Update();
		update.pull("mobiles", basicDBObject);
		WriteResult upsert = mongoTemplate.upsert(query, update, Account.class);
		if (upsert.getN() <= 0) {
			return -1;
		}
		return 1;
	}

}
