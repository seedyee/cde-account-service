package io.cde.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cde.account.dao.Interface.AccountRepository;
import io.cde.account.dao.Interface.MobileRepository;
import io.cde.account.domaim.Account;
import io.cde.account.domaim.Mobile;
import io.cde.account.domaim.i18n.error.ErrorStatus;
import io.cde.account.tools.ErrorMessageSourceHandler;
import io.cde.account.tools.ResultUtils;

/**
 * @author lcl
 * @createDate 2016年11月13日下午10:12:47
 *
 */
@Service
public class MobileService {
	
	@Autowired
	private ErrorMessageSourceHandler errorHandler;
	
	@Autowired
	private MobileRepository mobileRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * 根据用户id获取邮箱信息
	 * @param accountId 用户id
	 * @return 返回用户邮箱信息
	 */
	public Object getMobiles(String accountId) {
		List<Mobile> mobiles = new ArrayList<>();
		mobiles = mobileRepository.findByAccountId(accountId);
		if (mobiles.size() > 0) {
			Account account = accountRepository.findById(accountId);
			mobiles = this.setDefaultAndPublicMobile(mobiles, account.getMobile(), account.getIsPublicMobile());
			return ResultUtils.result(mobiles);
		}
		return ResultUtils.resultNullError();
	}
	/**
	 * 修改电话信息
	 * @param mobile 次改的电话对象
	 * @return 返回修改操作结果
	 */
	public Object updateMobile(Mobile mobile) {
		Mobile formMobile = mobileRepository.findById(mobile.getId());
		if (formMobile == null) {
			return ResultUtils.resultError(ErrorStatus.ACCOUNT_NOT_MOBILE.getCode(), errorHandler.getMessage(ErrorStatus.ACCOUNT_NOT_MOBILE.toString()));
		}
		formMobile.setIsVerified(mobile.getIsVerified());;
		mobileRepository.save(formMobile);
		return ResultUtils.resultNullError();
	}
	/**
	 * 添加电话号码信息
	 * @param mobile 添加的电话对象
	 * @return 返回添加的结果
	 */
	public Object addMobile(Mobile mobile) {
		Mobile checkEmail = mobileRepository.findByMobile(mobile.getMobile());
		if (checkEmail != null) {
			return ResultUtils.resultError(ErrorStatus.MOBILE_EXISTED.getCode(), errorHandler.getMessage(ErrorStatus.MOBILE_EXISTED.toString()));
		}
		mobileRepository.save(mobile);
		return ResultUtils.resultNullError();
	}
	/**
	 * 删除电话信息
	 * @param accountId 用户id
	 * @param id 电话id
	 * @return 返回删除操作的结果
	 */
	public Object deleteMobile(String accountId, String id) {
		Mobile checkDefaultMobile = mobileRepository.findById(id);
		Account account = accountRepository.findById(accountId);
		if (checkDefaultMobile == null || account == null ) {
			return ResultUtils.resultError(ErrorStatus.ACCOUNT_NOT_MOBILE.getCode(), errorHandler.getMessage(ErrorStatus.ACCOUNT_NOT_MOBILE.toString()));
		}
		if (checkDefaultMobile.getMobile().equals(account.getMobile())) {
			return ResultUtils.resultError(ErrorStatus.ILLEGAL_DELETE_MOBILE.getCode(), errorHandler.getMessage(ErrorStatus.ILLEGAL_DELETE_MOBILE.toString()));
		}
		mobileRepository.delete(checkDefaultMobile);
		return ResultUtils.resultNullError();
	}
	/**
	 * @param mobiles 用户所有电话
	 * @param mobile 用户默认电话
	 * @param isPublicMobile 默认电话是否公开
	 * @return
	 */
	private List<Mobile> setDefaultAndPublicMobile(List<Mobile> mobiles, String mobile, boolean isPublicMobile) {
		for (Mobile mobile2 : mobiles) {
			if (mobile2.getMobile().equals(mobile)) {
				mobile2.setIsDefault(true);
				mobile2.setIsPublic(isPublicMobile);
			}
		}
		return mobiles;
	}
}
