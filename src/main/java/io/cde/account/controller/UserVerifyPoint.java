package io.cde.account.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.beans.Email;
import io.cde.account.beans.Mobile;
import io.cde.account.beans.User;
import io.cde.account.server.UserService;
/**
* @author 作者 Fangcai Liao
* @version 创建时间：Oct 31, 2016 10:52:29 PM
* 类说明
*/
@RestController
public class UserVerifyPoint {

  @Autowired
  private UserService userService;
  /**
  * 根据ID获取用户信息API
  */
  @RequestMapping(value = "/users/{id}", method = RequestMethod.GET)
  public Map<String, Object> getUserById(@PathVariable(required = true, value = "id")String id){
    Map<String, Object> result = userService.getUserById(id);
    return result;
  }
  /**
  * 根据用户id修改用户基本信息
  * @param user
  * @return
  */
  @RequestMapping(value = "/users/{userId}/bsinformation", method = RequestMethod.POST)
  public Map<String, Object> updateUserInfo(@PathVariable(name = "userId",required = true)String userId,@ModelAttribute("user")User user, HttpServletRequest request) {
    Map<String, Object> message = userService.updateUserInfo(user, request);
    return message;
  }
  /**
  * 根据邮箱id修改邮箱信息
  * @param id
  * @param email
  * @return 返回该邮箱所属用户的所有邮箱信息或错误信息
  */
  @RequestMapping(value = "/users/{userId}/emails", method = RequestMethod.POST)
  public Map<String, Object> updateEmail(@PathVariable(name = "userId", required = true)String userId, @ModelAttribute(name = "email")Email email){
    Map<String, Object> message = null;
    message = userService.updateEmail(userId, email);
    return message;
  }
  /**
  * 修改用户mobile api
  * @param id
  * @param mobile
  * @return
  */
  @RequestMapping(value = "/users/{userId}/mobiles", method = RequestMethod.POST)
  public Map<String, Object> updateMobile(@PathVariable(name = "userId", required = true)String userId, @ModelAttribute(name = "mobile")Mobile mobile){
    Map<String, Object> message = null;
    message = userService.updateMobile(userId, mobile);
    return message;
  }
  /**
  * 为用户增加邮箱
  * @param id
  * @param email
  * @return
  */
  @RequestMapping(value = "/users/{userId}/email", method = RequestMethod.POST)
  public Map<String, Object> addEmail(@PathVariable(name = "userId", required = true)String userId, @RequestParam(name = "email", required = true)String email){
    Map<String, Object> message = null;
    message = userService.addEmail(userId, email);
    return message;
  }
  /**
  * 添加电话号码
  * @param id
  * @param mobile
  * @return
  */
  @RequestMapping(value = "/users/{userId}/mobile", method = RequestMethod.POST)
  public Map<String, Object> addMobile(@PathVariable(name = "userId", required = true)String userId, @RequestParam(name = "mobile", required = true)String mobile){
    Map<String, Object> message = null;
    message = userService.addMobile(userId, mobile);
    return message;
  }
  /**
  * 删除email或mobile
  * @param mobiles
  * @param emails
  * @return
  */
  @RequestMapping(value = "/users/{userId}/delete", method = RequestMethod.POST)
  public Map<String, Object> delete(@PathVariable(name = "userId", required = true)String userId, @RequestParam(name = "mobiles[]", required = false)List<String> mobiles, @RequestParam(name = "emails[]", required = false)List<String> emails){
    Map<String, Object> message = null;
    message = userService.deleteEmailOrMobile(userId, mobiles, emails);
    return message;
  }
}
