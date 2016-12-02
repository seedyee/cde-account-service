package io.cde.account.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.domain.ErrorInfo;
import io.cde.account.domain.Mobile;
import io.cde.account.domain.i18n.Error;
import io.cde.account.exception.AccountNotFundException;
import io.cde.account.exception.BizException;
import io.cde.account.service.impl.MobileServiceImpl;

/**
 * @author lcl
 * @createDate 2016年12月2日上午10:30:52
 *
 */
@RestController
@RequestMapping(value = "/accounts")
public class MobileController {
    
	@Autowired
	private MobileServiceImpl mobileService;
	
	/**
	 * 获取用户电话信息
	 * @param accountId
	 * @return
	 */
	@RequestMapping(value = "/{accountId}/mobiles", method = RequestMethod.GET)
	public List<Mobile> getMobiles(@PathVariable String accountId) {
        List<Mobile> mobiles = new ArrayList<>();
        try {
			mobiles = mobileService.getMobiles(accountId);
		} catch (BizException e) {
			throw new AccountNotFundException();
		}
		return mobiles;
	}
	
	/**
	 * 修改用户的电话信息
	 * @param accountId 用户id
	 * @param mobileId 要修改的电话的id
	 * @param mobile 携带要修改的电话信息的对象
	 * @return 返回操作的结果
	 */
	@RequestMapping(value = "/{accountId}/mobiles/{mobileId}", method = RequestMethod.POST)
	public ErrorInfo updateMobile(@PathVariable String accountId, @PathVariable String mobileId, @RequestParam(name = "isVerified") boolean isVerified) {
		try {
			mobileService.updateMobile(accountId, mobileId, isVerified);
		} catch (BizException e) {
			return this.handException(e);
		}
		return null;
	}
	
	/**
	 * 增加用户电话信息
	 * @param accountId 用户id
	 * @param mobile 携带要添加的电话的结果
	 * @return 返回添加操作的结果
	 */
	@RequestMapping(value = "/{accountId}/mobiles", method = RequestMethod.POST)
	public ErrorInfo addMobile(@PathVariable String accountId, @ModelAttribute(name = "mobile") Mobile mobile) {
        try {
			mobileService.addMobile(accountId, mobile);
		} catch (BizException e) {
			return this.handException(e);
		}
		return null;
	}
	
	/**
	 * 删除用户电话信息
	 * @param accountId 用户id
	 * @param mobileId 要删除的电话的id
	 * @return 删除操作的结果
	 */
	@RequestMapping(value = "/{accountId}/mobiles/{mobileId}", method = RequestMethod.DELETE)
	public Object deleteMobile(@PathVariable String accountId, @PathVariable String mobileId) {
        try {
			mobileService.deleteMobile(accountId, mobileId);
		} catch (BizException e) {
			return this.handException(e);
		}
		return null;
	}
	
	/**
	 * 异常处理
	 * @param e catch到的异常
	 * @return 若是AccountNotFundException则抛出，否则返回异常对象信息
	 */
	private ErrorInfo handException(BizException e) {
		if (e.getCode() == 100001 || e.getCode() == Error.UNASSOCIATED_ACCOUNT_AND_MOBILE.getCode()) {
			throw new AccountNotFundException();
		}
		return new ErrorInfo(e.getCode(), e.getMessage());
	}
}
