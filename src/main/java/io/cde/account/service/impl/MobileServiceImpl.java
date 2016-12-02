package io.cde.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cde.account.dao.impl.MobileDaoImpl;
import io.cde.account.domain.Account;
import io.cde.account.domain.Email;
import io.cde.account.domain.Mobile;
import io.cde.account.exception.BizException;
import io.cde.account.service.MobileService;
import io.cde.account.tools.AccountCheck;
import io.cde.account.tools.ErrorMessageSourceHandler;

/**
 * @author lcl
 *
 */
@Service
public class MobileServiceImpl implements MobileService {
    
	@Autowired
	private ErrorMessageSourceHandler errorHandler;
	
	@Autowired
	private AccountCheck accountCheck;
	
	@Autowired
	private MobileDaoImpl mobileDao;
	
	
	/* (non-Javadoc)
	 * @see io.cde.account.service.MobileService#getMobiles(java.lang.String)
	 */
	@Override
	public List<Mobile> getMobiles(String accountId) throws BizException {
		List<Mobile> mobiles = new ArrayList<>();
		Account account = accountCheck.checkAccountExistedById(accountId);
		mobiles = account.getMobiles();
		if (mobiles.size() > 0) {
			mobiles = this.assembleData(mobiles, account.getMobile(), account.isPublicMobile());
		}
		return mobiles;
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.MobileService#addMobile(java.lang.String, io.cde.account.domain.Mobile)
	 */
	@Override
	public void addMobile(String accountId, Mobile mobile) throws BizException {
		accountCheck.checkAccountExistedById(accountId);
		accountCheck.checkMobileExistedByMobile(mobile.getMobile());
		mobile.setMobileId(new ObjectId().toString());
		int addMobile = mobileDao.addMobile(accountId, mobile);
		if (addMobile <= 0) {
			throw new BizException(1, "添加电话信息失败");
		}

	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.MobileService#updateMobile(java.lang.String, boolean)
	 */
	@Override
	public void updateMobile(String accountId, String mobileId, boolean isVerified) throws BizException {
		accountCheck.checkAccountMobile(accountId, mobileId);
        int updateMobile = mobileDao.updateMobile(accountId, mobileId, isVerified);
        if (updateMobile <= 0) {
			throw new BizException(1, "修改电话信息失败");
		}
	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.MobileService#deleteMobile(java.lang.String, java.lang.String)
	 */
	@Override
	public void deleteMobile(String accountId, String mobileId) throws BizException {
		accountCheck.checkAccountMobile(accountId, mobileId);
        int deleteMobile = mobileDao.deleteMobile(accountId, mobileId);
        if (deleteMobile <= 0) {
			throw new BizException(1, "删除电话失败");
		}
	}
    
	/**
	 * 组装邮箱信息.
	 * 
	 * @param mobiles
	 * @param defaultMobile
	 * @param isPublic
	 * @return
	 */
	private List<Mobile> assembleData(List<Mobile> mobiles, String defaultMobile,boolean isPublic) {
        for (Mobile mobile : mobiles) {
			if (mobile.getMobile().equals(defaultMobile)) {
				mobile.setDefault(true);
				mobile.setPublic(isPublic);
			}
		} 
        return mobiles;
    }
}
