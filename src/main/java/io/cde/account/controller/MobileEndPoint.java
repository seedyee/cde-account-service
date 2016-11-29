package io.cde.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.domain.Mobile;
import io.cde.account.service.MobileService;

/**
 * @author lcl
 * @createDate 2016年11月13日下午10:15:27
 *
 */
@RestController
@RequestMapping(value = "/accounts")
public class MobileEndPoint {
	
	@Autowired
	private MobileService mobileService;
	
	private Object message;
	
	/**
	 * 获取用户的电话信息
	 * @param accountId 用户id
	 * @return 返回用户的电话信息或是错误的操作信息
	 */
	@RequestMapping(value = "/{accountId}/mobiles", method = RequestMethod.GET)
	public Object getMobiles(@PathVariable String accountId) {
		message = mobileService.getMobiles(accountId);
		return message;
	}
	/**
	 * 修改用户的电话信息
	 * @param accountId 用户id
	 * @param mobileId 要修改的电话的id
	 * @param mobile 携带要修改的电话信息的对象
	 * @return 返回操作的结果
	 */
	@RequestMapping(value = "/{accountId}/mobiles/{mobileId}", method = RequestMethod.POST)
	public Object updateMobile(@PathVariable String accountId, @PathVariable String mobileId, @ModelAttribute(name = "mobile") Mobile mobile) {
		mobile.setId(mobileId);
		message = mobileService.updateMobile(mobile);
		return message;
	}
	/**
	 * 增加用户电话信息
	 * @param accountId 用户id
	 * @param mobile 携带要添加的电话的结果
	 * @return 返回添加操作的结果
	 */
	@RequestMapping(value = "/{accountId}/mobiles", method = RequestMethod.POST)
	public Object addMobile(@PathVariable String accountId, @ModelAttribute(name = "mobile") Mobile mobile) {
		message = mobileService.addMobile(mobile);
		return message;
	}
	/**
	 * 删除用户电话信息
	 * @param accountId 用户id
	 * @param mobileId 要删除的电话的id
	 * @return 删除操作的结果
	 */
	@RequestMapping(value = "/{accountId}/mobiles/{mobileId}", method = RequestMethod.DELETE)
	public Object deleteMobile(@PathVariable String accountId, @PathVariable String mobileId) {
		message = mobileService.deleteMobile(accountId, mobileId);
		return message;
	}
}
