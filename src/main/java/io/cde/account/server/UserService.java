package io.cde.account.server;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.cde.account.beans.Email;
import io.cde.account.beans.Mobile;
import io.cde.account.beans.User;
import io.cde.account.dao.EmailDaoImp;
import io.cde.account.dao.MobileDaoImp;
import io.cde.account.dao.UserDaoImp;
import io.cde.account.dao.Interface.EmailDao;
import io.cde.account.dao.Interface.MobileDao;
import io.cde.account.dao.Interface.UserDao;
import io.cde.account.tools.MergeObjectUtils;


/**
* @author 作者 Fangcai Liao
* @version 创建时间：Oct 26, 2016 11:16:00 AM
* 类说明
*/
@Service
public class UserService {

  @Autowired
  UserDaoImp userDaoImp;

  @Autowired
  UserDao userDao;

  @Autowired
  EmailDao emailDao;

  @Autowired
  EmailDaoImp emailDaoImp;

  @Autowired
  MobileDao mobileDao;

  @Autowired
  MobileDaoImp mobileDaoImp;
  /**
  * 用户注册服务
  * @param name
  * @param email
  * @param password
  * @return 注册以后的用户信息，或是错误信息
  */
  @Transactional
  public Map<String, Object> registUser(String username, String email, String password) {
    Map<String, Object> result = new HashMap<>();
    //根据用户名判断用户是否存在
    List<Map<String, Object>> checkUser =  userDaoImp.getUserByUsername(username);
    //根据邮箱名，判断邮箱是否被注册过或者关联过
    int checkEmail = emailDaoImp.checkByEmail(email); 
    if (checkUser.size() > 0 || checkEmail > 0) {
      result.put("error", "10001  用户已经存在");
      return result;
    }
    User newUser = new User();
    Email userEmail = new Email();
    newUser.setUsername(username);
    newUser.setPassword(password);
    newUser.setTimestamp(new Date());
    newUser.setEmail(email);
    newUser.setIsPublicEmail("1");
    User insertUser = userDao.save(newUser);
    userEmail.setUserId(insertUser.getId());
    userEmail.setEmail(email);
    userEmail.setIsVerified("0");
    emailDao.save(userEmail);
    result.put("error", "null");
    return result;
  }
  /**
  * 用户登录服务
  * @param principal 用户名或邮箱
  * @param password 密码
  * @return
  */
  @Transactional
  public Map<String, Object> sigin(String principal, String password){
    Map<String, Object> result = new HashMap<>();
    List<Map<String, Object>> userList = null;
    Email email = null;
    if (principal != null && password != null) {
      //使用用户名登录的情况
      userList =  userDaoImp.getUserByUsername(principal);
      if (userList.size() > 0) {//使用用户名登录的情况
        Map<String,Object> userMap= (Map<String,Object>) userList.get(0);
        if (userMap.get("password").equals(password)) {
          result.put("error", "null");
          return result;
        }else {
          result.put("error", "10002  密码错误");
          return result;
        }
      }else {//邮箱存在且是默认邮箱才可以登录
        email = emailDaoImp.getByDefalutEmail(principal);
        if (email != null) {
          List<Map<String, Object>> userById = userDaoImp.getUserById(email.getUserId());
          Map<String, Object> userMap = userById.get(0);
          if (userMap.get("password").equals(password)) {
            result.put("error", "null");
            return result;
          }else {
            result.put("error", "10002  密码错误");
            return result;
          }
        }
      }
    }
    //处理没有传递邮箱或是用户名的错误信息
    result.put("error", "10005  用户不存在");
    return result;
  }

  public Map<String, Object> signout(String id) {
    Map<String, Object> result = new HashMap<>();
    result.put("error", "null");
    return result;
  }
  /**
  * 根据用户id获取用户信息
  * @param id 用户id
  * @return 用户信息或是错误信息
  */
  @Transactional
  public Map<String, Object> getUserById(String id) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> userInfo = new HashMap<>();
    List<Map<String,Object>> user = null;
    List<Map<String,Object>> emails = null;
    List<Map<String,Object>> mobiles = null;
    user = userDaoImp.getUserById(id);
    if (user.size() > 0) {
      emails = emailDaoImp.getEmailsByUserId(id);
      mobiles = mobileDaoImp.getMobilesByUserId(id);
      userInfo.put("basicsInformation", user.get(0));
      userInfo.put("emails", emails);
      userInfo.put("mobiles", mobiles);

      result.put("result", userInfo);
      result.put("error", 00000);
      return result;
    }else {
      result.put("error", 10005 + "用户不存在");
      return result;
    }
  }
  /**
  * 修改用户基本信息的service,这里是用jpa实现
  * @param user
  * @return
  */
  @Transactional
  public Map<String, Object> updateUserInfo(User user, HttpServletRequest request) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> basicsInformation = new HashMap<>();
    User updateResult = null;
    Email checkEmail = null;
    Mobile checkMobile = null;
    User formUser = userDao.findById(user.getId());
    if (formUser != null) {
      if (user.getUsername() != null) {//需要修改用户名
        User checkUser = userDao.findByUsername(user.getUsername());
        if (checkUser != null) {//要修改的用户名没有被注册过
          result.put("error", 10001 + "用户已经存在");
          return result;
        }
      }
      if (user.getPassword() != null) {//需要修改密码
        if (request.getParameter("newPassword") != null) {
          if (user.getPassword().equals(formUser.getPassword())) {
            user.setPassword(request.getParameter("newPassword"));
          }else {
          result.put("error", 10002 + "密码错误");
            return result;
          }
        }else {
          result.put("error", 10003 + "要修改的密码为空");
          return result;
        }
      }
      if (user.getEmail() != null) {
        checkEmail = emailDao.findByUserIdAndEmail(user.getId(), user.getEmail());
        if (checkEmail == null) {
            result.put("error", "该用户还没有关联该邮箱，不能设置该邮箱为默认邮箱");
            return result;
          }else {
          if (!checkEmail.getIsVerified().equals("1")) {
            result.put("error", "该邮箱还没有认证，不能设置该邮箱为默认邮箱");
            return result;
          }
        }
      }
      if (user.getMobile() != null) {
        checkMobile = mobileDao.findByUserIdAndMobile(user.getId(), user.getMobile());
        if (checkMobile == null) {
          result.put("error", "该用户还没有关联该电话，不能设置该电话为默认电话");
            return result;
          }else {
          if (!checkMobile.getIsVerified().equals("1")) {
            result.put("error", "该电话还没有认证，不能设置该电话为默认电话");
            return result;
          }
        }
      }
      formUser = (User) MergeObjectUtils.merge(formUser, user);
      updateResult = userDao.save(formUser);
      basicsInformation.put("basicsInformation", updateResult);
      result.put("result", basicsInformation);
      result.put("error", "null");
      return result;
    }
    result.put("error", 10005 + "用户不存在");
    return result;
  }
  /**
  * 根据邮箱id修改邮箱信息，并返回该邮箱所属用户的所有邮箱信息,这里只修改邮箱是否认证
  * @param userId
  * @param email
  * @return
  */
  @Transactional
  public Map<String, Object> updateEmail(String userId, Email email) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> emailInfo = new HashMap<>();
    List<Map<String,Object>> updateResult = null;
    Email formEmail = emailDao.findById(email.getId());
    if (email != null && email.getId() != null) {
      formEmail = emailDao.findById(email.getId());
      if (formEmail == null) {
          result.put("error", "该邮箱没有关联用户");
          return result;
        }else {
          if (email.getIsVerified() != null) {
            formEmail.setIsVerified(email.getIsVerified());
            emailDao.saveAndFlush(formEmail);
            updateResult = emailDaoImp.getEmailsByUserId(userId);
          if (updateResult != null) {
            emailInfo.put("emails", updateResult);
            result.put("result", emailInfo);
            result.put("error", "null");
            return result;
          }
        }
      }
    }
    result.put("error", "没有传递参数");
    return result;
  }
  /**
  * 修改用户电话信息
  * @param userId
  * @param mobile
  * @return
  */
  @Transactional
  public Map<String, Object> updateMobile(String userId, Mobile mobile) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> mobileInfo = new HashMap<>();
    List<Map<String,Object>> updateResult = null;
    Mobile formMobile = null;
    if (mobile != null && mobile.getId() != null) {
      formMobile = mobileDao.findById(mobile.getId());
      if (formMobile == null) {
        result.put("error", "该电话没有关联用户");
        return result;
      }else {
        if(mobile.getIsVerified() != null) {
          formMobile.setIsVerified(mobile.getIsVerified());
          mobileDao.saveAndFlush(formMobile);
          updateResult = mobileDaoImp.getMobilesByUserId(userId);
        if(updateResult != null) {
          mobileInfo.put("mobiles", updateResult);
          result.put("result", mobileInfo);
          result.put("error", "null");
          return result;
          }
        }
      }
    }
    result.put("error", "没有传递参数");
    return result;
  }
  /**
  * 为用户添加邮箱
  * @param userId
  * @param email
  * @return
  */
  @Transactional
  public Map<String, Object> addEmail(String userId, String email){
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> emailInfo = new HashMap<>();
    List<Map<String,Object>> addResult = null;
    Email checkEmail = null;
    if (email != null) {
      checkEmail = emailDao.findByEmail(email);
      if (checkEmail != null) {
        result.put("error", "该邮箱已经被使用过");
        return result;
      }
      checkEmail = new Email();
      checkEmail.setUserId(userId);
      checkEmail.setEmail(email);
      checkEmail.setIsVerified("0");
      emailDao.saveAndFlush(checkEmail);
      addResult = emailDaoImp.getEmailsByUserId(userId);
      emailInfo.put("emails", addResult);
      result.put("result", emailInfo);
      result.put("error","null");
      return result;
    }
    result.put("error", "没有传递参数");
    return result;
  }
  /**
  * 添加电话service
  * @param id
  * @param mobile
  * @return
  */
  @Transactional
  public Map<String, Object> addMobile(String userId, String mobile) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> mobileInfo = new HashMap<>();
    List<Map<String,Object>> addResult = null;
		Mobile checkMobile = null;
		if (mobile != null) {
      checkMobile = mobileDao.findByMobile(mobile);
      if (checkMobile != null) {
        result.put("error", "该号码已经被使用过");
        return result;
      }
      checkMobile = new Mobile();
      checkMobile.setUserId(userId);
      checkMobile.setIsVerified("0");
      checkMobile.setMobile(mobile);
      mobileDao.saveAndFlush(checkMobile);
      addResult = mobileDaoImp.getMobilesByUserId(userId);
      mobileInfo.put("mobiles", addResult);
      result.put("result", mobileInfo);
      result.put("erro", "null");
      return result;
    }
    result.put("error", "没有传递参数");
    return result;
  }
  /**
  * 删除email或是mobile
  * @param mobiles
  * @param emails
  * @return
  */
	public Map<String, Object> deleteEmailOrMobile(String userId, List<String> mobiles, List<String> emails) {
    Map<String, Object> result = new HashMap<>();
    Map<String, Object> emailRetrun = new HashMap<>();
    Map<String, Object> mobileRetrun = new HashMap<>();
    List<Map<String, Object>> mobileInfo = null;
    List<Map<String, Object>> emailInfo = null;
    User user = null;
    Email checkDefaultEmail = null;
    Mobile checkDefaultMobile = null;
    int[] deleteEmails = null;
    int[] deleteMobiles = null;
    if (emails != null || mobiles != null) {
      if (emails != null && emails.size() > 0) {
        user = userDao.findById(userId);
        if (user !=null) {
          checkDefaultEmail = emailDao.findByEmail(user.getEmail());
          if (checkDefaultEmail != null) {
            if (emails.contains(checkDefaultEmail.getId())) {//默认邮箱不能删除
              result.put("error", "默认邮箱不能删除");
              return result;
            }
        	}
        	deleteEmails = emailDaoImp.deleteEmails(emails);
          if (deleteEmails != null) {
            emailInfo = emailDaoImp.getEmailsByUserId(userId);
            emailRetrun.put("emails", emailInfo);
            result.put("result", emailRetrun);
          }
        }
      }
      if (mobiles != null && mobiles.size() > 0) {//默认电话不可以删除
        user = userDao.findById(userId);
        if (user !=null) {
          checkDefaultMobile = mobileDao.findByMobile(user.getMobile());
          if (checkDefaultMobile != null) {
            if (mobiles.contains(checkDefaultMobile.getId())) {//默认邮箱不能删除
              result.put("error", "默认电话不能删除");
              return result;
            }
        	}
        	deleteMobiles = mobileDaoImp.deleteMobiles(mobiles);
        	if (deleteMobiles != null) {
            mobileInfo = mobileDaoImp.getMobilesByUserId(userId);
            mobileRetrun.put("mobiles", mobileInfo);
            result.put("result", mobileRetrun);
        	}
        }
      }
      result.put("error", "null");
      return result;
    }
    result.put("error", "没有参数传入");
    return result;
	} 
}
