package io.cde.account.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import io.cde.account.dao.impl.MobileDaoImpl;
import io.cde.account.domain.Account;
import io.cde.account.domain.Mobile;
import io.cde.account.domain.i18n.SystemError;
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
	@Cacheable(cacheNames = "mobiles", key = "'mobile:' + #accountId")
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
	@Caching(evict = {@CacheEvict(cacheNames = "accounts", key = "'account:' + #accountId"),
			@CacheEvict(cacheNames = "mobiles", key = "'mobile:' + #accountId")})
	public void addMobile(String accountId, Mobile mobile) throws BizException {
		accountCheck.checkAccountExistedById(accountId);
		accountCheck.checkMobileExistedByMobile(mobile.getMobile());
		mobile.setMobileId(new ObjectId().toString());
		int addMobile = mobileDao.addMobile(accountId, mobile);
		if (addMobile <= 0) {
			throw new BizException(SystemError.INSERT_FAILED.getCode(), errorHandler.getMessage(SystemError.INSERT_FAILED.toString()));
		}

	}

	/* (non-Javadoc)
	 * @see io.cde.account.service.MobileService#updateMobile(java.lang.String, boolean)
	 */
	@Override
	@Caching(evict = {@CacheEvict(cacheNames = "accounts", key = "'account:' + #accountId"),
			@CacheEvict(cacheNames = "mobiles", key = "'mobile:' + #accountId")})
	public void updateMobile(String accountId, String mobileId, boolean isVerified) throws BizException {
		accountCheck.checkAccountMobile(accountId, mobileId);
        int updateMobile = mobileDao.updateMobile(accountId, mobileId, isVerified);
        if (updateMobile <= 0) {
        		throw new BizException(SystemError.UPDATE_FAILED.getCode(), errorHandler.getMessage(SystemError.UPDATE_FAILED.toString()));
		}
	}
    
	/* (non-Javadoc)
	 * @see io.cde.account.service.MobileService#updatePublicMobile(java.lang.String, boolean)
	 */
	@Override
	@Caching(evict = {@CacheEvict(cacheNames = "accounts", key = "'account:' + #accountId"),
			@CacheEvict(cacheNames = "mobiles", key = "'mobile:' + #accountId")})
	public void updatePublicMobile(String accountId, boolean isPublic) throws BizException {
		accountCheck.checkAccountExistedById(accountId);
		int updateEmail = mobileDao.updatePublicMobile(accountId, isPublic);
        if (updateEmail <= 0) {
        		throw new BizException(SystemError.UPDATE_FAILED.getCode(), errorHandler.getMessage(SystemError.UPDATE_FAILED.toString()));
		}
	}
	
	/* (non-Javadoc)
	 * @see io.cde.account.service.MobileService#deleteMobile(java.lang.String, java.lang.String)
	 */
	@Override
	@Caching(evict = {@CacheEvict(cacheNames = "accounts", key = "'account:' + #accountId"),
			@CacheEvict(cacheNames = "mobiles", key = "'mobile:' + #accountId")})
	public void deleteMobile(String accountId, String mobileId) throws BizException {
		accountCheck.checkAccountMobile(accountId, mobileId);
        int deleteMobile = mobileDao.deleteMobile(accountId, mobileId);
        if (deleteMobile <= 0) {
        		throw new BizException(SystemError.DELETE_FAILED.getCode(), errorHandler.getMessage(SystemError.DELETE_FAILED.toString()));
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
