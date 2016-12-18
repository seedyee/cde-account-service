package io.cde.account.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.mongodb.WriteResult;

import io.cde.account.dao.AccountDao;
import io.cde.account.domain.Account;

/**
 * @author lcl
 */
@Repository
public class AccountDaoImpl implements AccountDao {

    @Autowired
    private MongoTemplate mongoTemPlate;

    /* (non-Javadoc)
     * @see io.cde.account.dao.AccountDao#createAccount(io.cde.account.domain.Account)
     */
    @Override
    public int createAccount(final Account account) {
        mongoTemPlate.insert(account, "account");
        return 1;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.AccountDao#findById(java.lang.String)
     */
    @Override
    public Account findById(final String id) {
        final Account account;
        final Query query = Query.query(Criteria.where("_id").is(id));
        account = mongoTemPlate.findOne(query, Account.class);
        return account;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.AccountDao#findByName(java.lang.String)
     */
    @Override
    public Account findByName(final String name) {
        final Account account;
        final Query query = Query.query(Criteria.where("name").is(name));
        account = mongoTemPlate.findOne(query, Account.class);
        return account;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.AccountDao#updateAccount(io.cde.account.domain.Account)
     */
    @Override
    public int updateAccount(final Account account) {
        final Query query = Query.query(Criteria.where("_id").is(account.getId()));
        final Update update = Update.update("realName", account.getRealName()).set("email", account.getEmail()).
                set("company", account.getCompany()).set("business", account.getBusiness()).
                set("address", account.getAddress()).set("position", account.getPosition()).
                set("personal", account.getPersonal()).set("mobile", account.getMobile());
        final WriteResult updateFirst = mongoTemPlate.updateFirst(query, update, Account.class);
        if (updateFirst.getN() <= 0) {
            return -1;
        }
        return 1;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.AccountDao#updateName(java.lang.String, java.lang.String)
     */
    @Override
    public int updateName(final String accountId, final  String name) {
        final Query query = Query.query(Criteria.where("_id").is(accountId));
        final Update update = Update.update("name", name);
        final WriteResult updateFirst = mongoTemPlate.updateFirst(query, update, Account.class);
        if (updateFirst.getN() <= 0) {
            return -1;
        }
        return 1;
    }

    /* (non-Javadoc)
     * @see io.cde.account.dao.AccountDao#updatePassword(java.lang.String, java.lang.String)
     */
    @Override
    public int updatePassword(final String accountId, final  String password) {
        final Query query = Query.query(Criteria.where("_id").is(accountId));
        final Update update = Update.update("password", password);
        final WriteResult updateFirst = mongoTemPlate.updateFirst(query, update, Account.class);
        if (updateFirst.getN() <= 0) {
            return -1;
        }
        return 1;
    }
}
