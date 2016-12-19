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
 */
@Repository
public class MobileDaoImpl implements MobileDao {

    /**
     * MongoTemplate对象.
     */
    private MongoTemplate mongoTemplate;

    /**
     * 通过构造器注入对象.
     *
     * @param mongoTemPlate mongoTemPlate对象
     */
    @Autowired
    public MobileDaoImpl(final MongoTemplate mongoTemPlate) {
        this.mongoTemplate = mongoTemPlate;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.MobileDao#getMobileById(java.lang.String, java.lang.String)
     */
    @Override
    public Account findAccountByMobileId(final String accountId, final String mobileId) {
        final Account account;
        final Query query = Query.query(Criteria.where("_id").is(accountId).and("mobiles.mobileId").is(mobileId));
        account = mongoTemplate.findOne(query, Account.class);
        return account;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.MobileDao#isMobileExisted(java.lang.String)
     */
    @Override
    public boolean isMobileExisted(final String mobile) {
        final boolean isEmailExisted;
        final Query query = Query.query(Criteria.where("mobiles.mobile").is(mobile));
        isEmailExisted = mongoTemplate.exists(query, "account");
        return isEmailExisted;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.MobileDao#updateMobile(java.lang.String, io.cde.account.domain.Mobile)
     */
    @Override
    public int updateMobile(final String accountId, final String mobileId, final boolean isVerified) {
        final Query query = Query.query(Criteria.where("_id").is(accountId).and("mobiles.mobileId").is(mobileId));
        final Update update = Update.update("mobiles.$.isVerified", isVerified);
        final WriteResult updateFirst = mongoTemplate.updateFirst(query, update, Account.class);
        if (updateFirst.getN() <= 0) {
            return -1;
        }
        return 1;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.MobileDao#updatePublicMobile(java.lang.String, boolean)
     */
    @Override
    public int updatePublicMobile(final String accountId, final boolean isPublic) {
        final Query query = Query.query(Criteria.where("_id").is(accountId));
        final Update update = Update.update("isPublicMobile", isPublic);
        final WriteResult updateFirst = mongoTemplate.updateFirst(query, update, Account.class);
        if (updateFirst.getN() <= 0) {
            return -1;
        }
        return 1;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.MobileDao#addMobile(java.lang.String, io.cde.account.domain.Mobile)
     */
    @Override
    public int addMobile(final String accountId, final Mobile mobile) {
        final Query query = Query.query(Criteria.where("_id").is(accountId));
        final Update update = new Update();
        update.addToSet("mobiles", mobile);
        final WriteResult upsert = mongoTemplate.upsert(query, update, Account.class);
        if (upsert.getN() <= 0) {
            return -1;
        }
        return 1;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.MobileDao#deleteMobile(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteMobile(final String accountId, final String mobileId) {
        final Query query = Query.query(Criteria.where("_id").is(accountId));
        final BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("mobileId", mobileId);
        final Update update = new Update();
        update.pull("mobiles", basicDBObject);
        final WriteResult upsert = mongoTemplate.upsert(query, update, Account.class);
        if (upsert.getN() <= 0) {
            return -1;
        }
        return 1;
    }

}
