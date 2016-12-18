package io.cde.account.dao.impl;

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
 */
@Repository
public class EmailDaoImpl implements EmailDao {

    @Autowired
    private MongoTemplate mongoTemplate;

    /* (non-Javadoc)
     * @see io.cde.account.dao.EmailDao#getEmailById(java.lang.String, java.lang.String)
     */
    @Override
    public Account findAccountByEmailId(final String accountId, final String emailId) {
        final Account account;
        final Query query = Query.query(Criteria.where("_id").is(accountId).and("emails.emailId").is(emailId));
        account = mongoTemplate.findOne(query, Account.class);
        return account;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.EmailDao#getEmail(java.lang.String)
     */
    @Override
    public boolean isEmailExisted(final String email) {
        final boolean isEmailExisted;
        final Query query = Query.query(Criteria.where("emails.email").is(email));
        isEmailExisted = mongoTemplate.exists(query, "account");
        return isEmailExisted;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.EmailDao#updateEmail(boolean)
     */
    @Override
    public int updateEmail(final String accountId, final String emailId, final boolean isVerified) {
        final Query query = Query.query(Criteria.where("_id").is(accountId).and("emails.emailId").is(emailId));
        final Update update = Update.update("emails.$.isVerified", isVerified);
        final WriteResult updateFirst = mongoTemplate.updateFirst(query, update, Account.class);
        if (updateFirst.getN() <= 0) {
            return -1;
        }
        return 1;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.EmailDao#updatePublicEmail(java.lang.String, java.lang.String, boolean)
     */
    @Override
    public int updatePublicEmail(final String accountId, final boolean isPublic) {
        final Query query = Query.query(Criteria.where("_id").is(accountId));
        final Update update = Update.update("isPublicEmail", isPublic);
        final WriteResult updateFirst = mongoTemplate.updateFirst(query, update, Account.class);
        if (updateFirst.getN() <= 0) {
            return -1;
        }
        return 1;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.EmailDao#addEmail(java.lang.String, io.cde.account.domain.Email)
     */
    @Override
    public int addEmail(final String accountId, final Email email) {
        final Query query = Query.query(Criteria.where("_id").is(accountId));
        final Update update = new Update();
        update.addToSet("emails", email);
        final WriteResult upsert = mongoTemplate.upsert(query, update, Account.class);
        if (upsert.getN() <= 0) {
            return -1;
        }
        return 1;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.EmailDao#deleteEmail(java.lang.String, java.lang.String)
     */
    @Override
    public int deleteEmail(final String accountId, final String emailId) {
        final Query query = Query.query(Criteria.where("_id").is(accountId));
        final BasicDBObject basicDBObject = new BasicDBObject();
        basicDBObject.put("emailId", emailId);
        final Update update = new Update();
        update.pull("emails", basicDBObject);
        final WriteResult upsert = mongoTemplate.upsert(query, update, Account.class);
        if (upsert.getN() <= 0) {
            return -1;
        }
        return 1;
    }
}
