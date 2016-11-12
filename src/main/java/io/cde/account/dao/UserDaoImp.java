package io.cde.account.dao;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import io.cde.account.beans.User;
/**
* @author 作者 Fangcai Liao
* @version 创建时间：Oct 26, 2016 11:36:07 AM
* 类说明
*/
@Repository
public class UserDaoImp {

  @Autowired
  private JdbcTemplate jdbcTemplate;
 
  public int checkUserByUsername(String username) {
    int checkCount;
    String sql = "select count(f_id) from f_user where f_username = ?";
    checkCount = jdbcTemplate.queryForObject(sql, new Object[]{username}, Integer.class);
    return checkCount;
  }
  /**
  * 根据邮箱查询用户基本信息, 这里需要修改，没有使用复合查询
  * @param email email的名称
  * @return 返回查找出来的用户信息
  */
  public List<Map<String,Object>> getUserByEmail(String email) {
    List<Map<String,Object>> userList;
    String userId;
    String sql = "select f_user from f_email where f_email = ?";
    userId = jdbcTemplate.queryForObject(sql, new Object[]{email}, String.class);
    userList =  this.getUserById(userId);
    return userList;
  }
  /**
  * 根据id查询用户基本信息
  * @param id
  * @return
  */
  public List<Map<String,Object>> getUserById(String id) {
    List<Map<String,Object>> list = null;
    String sql = "select f_id id, f_username username, f_password password,"
      + "f_avatar avatar, f_company company, f_company_address companyAddress,"
      + "f_company_business companyBusiness, f_position position,"
      + "f_personal personal, f_timestamp timestamp,f_real_name realName,"
      + "f_email email, f_mobile mobile, f_public_email isPublicEmail, f_public_mobile isPublicMobile"
      + " from f_user where f_id = ?";
    list = jdbcTemplate.queryForList(sql, id);
    return list;
  }
  /**
  * 根据用户名查询用户基本信息
  * @param username
  * @return
  */
  public List<Map<String,Object>> getUserByUsername(String username) {
    List<Map<String,Object>> list = null;
    String sql = "select f_id id, f_username username, f_password password,"
      + "f_avatar avatar, f_company company, f_company_address companyAddress,"
      + "f_company_business companyBusiness, f_position position,"
      + "f_personal personal, f_timestamp timestamp,"
      + "f_real_name realName"
      + " from f_user where f_username = ?";
    try {
      list = jdbcTemplate.queryForList(sql, username);
    }catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return list;
  }
  /**
  * 修改用户基本信息
  * @param user 需要修改的信息实体
  * @param request 接收newPassword的请求，用来判断是否修改密码
  * @return 返回修改后的用户信息
  */
  public List<Map<String,Object>> updateUserInfo(User user) {
    List<Map<String,Object>> list = null;
    String sql = "update f_user set f_username = ?, f_password = ?, f_avatar = ?, "
      + "f_company = ?, f_company_address = ?, f_company_business = ?, f_position = ?,"
      + "f_personal = ?, f_timestamp = ?, f_real_name where f_id = ?";
    try {
      int update = jdbcTemplate.update(sql, new Object[]{user.getUsername(),user.getPassword(),
        user.getAvatar(), user.getCompany(), user.getCompanyAddress(), user.getCompanyBusiness(),
        user.getPersonal(), user.getTimestamp(), user.getRealName(),user.getId()});
      if (update == 1) {
        list = this.getUserById(user.getId());
      }
    }catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return list;
  }
}
