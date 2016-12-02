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
 * @createDate 2016年11月29日上午11:21:48
 *
 */
@Repository
public class AccountDaoImpl implements AccountDao {
    
	@Autowired
	private MongoTemplate mongoTemPlate;

	/* (non-Javadoc)
	 * @see io.cde.account.dao.AccountDao#createAccount(io.cde.account.domain.Account)
	 */
	@Override
	public int createAccount(Account account) {
		mongoTemPlate.insert(account, "account");
		return 1;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.AccountDao#findById(java.lang.String)
	 */
	@Override
	public Account findById(String id) {
		Account account = null;
		Query query = Query.query(Criteria.where("_id").is(id));
		account = mongoTemPlate.findOne(query, Account.class);
		return account;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.AccountDao#findByName(java.lang.String)
	 */
	@Override
	public Account findByName(String name) {
		Account account = null;
		Query query = Query.query(Criteria.where("name").is(name));
		account = mongoTemPlate.findOne(query, Account.class);
		return account;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.AccountDao#updateAccount(io.cde.account.domain.Account)
	 */
	@Override
	public int updateAccount(Account account) {
		Query query = Query.query(Criteria.where("_id").is(account.getId()));
		Update update = Update.update("realName", account.getRealName()).set("email", account.getEmail())
				.set("company", account.getCompany()).set("companyBusiness", account.getCompanyBusiness())
				.set("companyAddress", account.getCompanyAddress()).set("position", account.getPosition())
				.set("personal", account.getPersonal()).set("isPublicEmail", account.isPublicEmail())
				.set("isPublicMobile", account.isPublicMobile());
		WriteResult updateFirst = mongoTemPlate.updateFirst(query, update, Account.class);
		if (updateFirst.getN() <= 0) {
			return -1;
		}
		return 1;
	}
    
	/* (non-Javadoc)
	 * @see io.cde.account.dao.AccountDao#updateName(java.lang.String, java.lang.String)
	 */
	@Override
	public int updateName(String accountId, String name) {
		Query query = Query.query(Criteria.where("_id").is(accountId));
		Update update = Update.update("name", name);
		WriteResult updateFirst = mongoTemPlate.updateFirst(query, update, Account.class);
		if (updateFirst.getN() <= 0) {
			return -1;
		}
		return 1;
	}
	
	/* (non-Javadoc)
	 * @see io.cde.account.dao.AccountDao#updatePassword(java.lang.String, java.lang.String)
	 */
	@Override
	public int updatePassword(String accountId, String password) {
		Query query = Query.query(Criteria.where("_id").is(accountId));
		Update update = Update.update("password", password);
		WriteResult updateFirst = mongoTemPlate.updateFirst(query, update, Account.class);
		if (updateFirst.getN() <= 0) {
			return -1;
		}
		return 1;
	}
}