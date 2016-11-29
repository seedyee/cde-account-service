package io.cde.account.dao.impl;

import java.util.List;

import io.cde.account.dao.MobileDao;
import io.cde.account.domain.Mobile;

/**
 * @author lcl
 * @createDate 2016年11月29日下午5:55:10
 *
 */
public class MobileDaoImpl implements MobileDao {

	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#getMobileById(java.lang.String, java.lang.String)
	 */
	@Override
	public Mobile getMobileById(String accountId, String mobileId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#isMobileExisted(java.lang.String)
	 */
	@Override
	public boolean isMobileExisted(String mobile) {
		// TODO Auto-generated method stub
		return false;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#getMobiles(java.lang.String)
	 */
	@Override
	public List<Mobile> getMobiles(String accountId) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#updateMobile(java.lang.String, io.cde.account.domain.Mobile)
	 */
	@Override
	public int updateMobile(String accountId, Mobile mobile) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#addMobile(java.lang.String, io.cde.account.domain.Mobile)
	 */
	@Override
	public int addMobile(String accountId, Mobile mobile) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.dao.MobileDao#deleteMobile(java.lang.String, java.lang.String)
	 */
	@Override
	public int deleteMobile(String accountId, String mobileId) {
		// TODO Auto-generated method stub
		return 0;
	}

}
