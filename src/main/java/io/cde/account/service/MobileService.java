package io.cde.account.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.cde.account.dao.Interface.AccountRepository;
import io.cde.account.dao.Interface.MobileRepository;
import io.cde.account.domaim.Account;
import io.cde.account.domaim.Mobile;
import io.cde.account.tools.ResultUtils;

/**
 * @author lcl
 * @createDate 2016年11月13日下午10:12:47
 *
 */
@Service
public class MobileService {

	@Autowired
	private MobileRepository mobileRepository;
	
	@Autowired
	private AccountRepository accountRepository;
	
	/**
	 * 根据用户id获取邮箱信息
	 * @param accountId
	 * @return
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
	 * @param mobile
	 * @return
	 */
	public Object updateMobile(Mobile mobile) {
		Mobile formMobile = mobileRepository.findById(mobile.getId());
		if (formMobile == null) {
			return ResultUtils.resultError(1000017, "该电话没有关联用户");
		}
		formMobile.setIsVerified(mobile.getIsVerified());;
		mobileRepository.save(formMobile);
		return ResultUtils.resultNullError();
	}
	/**
	 * 添加电话号码信息
	 * @param mobile
	 * @return
	 */
	public Object addMobile(Mobile mobile) {
		Mobile checkEmail = mobileRepository.findByMobile(mobile.getMobile());
		if (checkEmail != null) {
			return ResultUtils.resultError(1000019, "该号码已经被使用过");
		}
		mobileRepository.save(mobile);
		return ResultUtils.resultNullError();
	}
	/**
	 * 删除电话信息
	 * @param accountId
	 * @param id
	 * @return
	 */
	public Object deleteMobile(String accountId, String id) {
		Mobile checkDefaultMobile = mobileRepository.findById(id);
		Account account = accountRepository.findById(accountId);
		if (checkDefaultMobile == null || account == null ) {
			return ResultUtils.resultError(1000013, "用户没有关联该电话号码");
		}
		if (checkDefaultMobile.getMobile().equals(account.getMobile())) {
			return ResultUtils.resultError(1000021, "默认电话不能删除");
		}
		mobileRepository.delete(checkDefaultMobile);
		return ResultUtils.resultNullError();
	}
	/**
	 * @param mobiles
	 * @param mobile
	 * @param isPublicMobile
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
