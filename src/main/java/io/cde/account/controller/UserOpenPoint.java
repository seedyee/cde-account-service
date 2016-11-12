package io.cde.account.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.cde.account.server.UserService;
/**
* @author 作者 Fangcai Liao
* @version 创建时间：Oct 26, 2016 11:09:14 AM
* 类说明
*/
@RestController
public class UserOpenPoint {

  @Autowired
  private UserService userService;
  /**
  * 用户注册api
  * @param name
  * @param email
  * @param password
  * @return
  */
  @RequestMapping(value = "/users", method = RequestMethod.POST)
  public Map<String, Object> regist(@RequestParam(value = "username", required = true) String username, @RequestParam(value = "email", required = true) String email, @RequestParam(value = "password", required = true) String password) {
    Map<String, Object> message = userService.registUser(username, email, password);
    return message;
  }
  /**
  * 用户登录接口
  * @param principal 用户名或邮箱账号
  * @param password 密码
  * @return 返回登录结果信息
  */
  @RequestMapping(value = "/authc/sigin", method = RequestMethod.POST)
  public Map<String, Object> sigin(@RequestParam(value = "principal", required = true) String principal, @RequestParam(value = "password", required = true) String password) {
    Map<String, Object> message = userService.sigin(principal,password);
    return message;
  }
}
