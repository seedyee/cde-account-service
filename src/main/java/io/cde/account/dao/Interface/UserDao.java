package io.cde.account.dao.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.cde.account.beans.User;
/**
* @author 作者 Fangcai Liao
* @version 创建时间：Oct 26, 2016 11:20:17 AM
* 使用jpa操作用户信息接口
*/
@Repository
public interface UserDao extends JpaRepository<User, String>{
  /**
  * 根据Id获取用户信息
  * @param id
  * @return 返回用户信息
  */
	User findById(String id);
  /**
  * 根据用户名查找用户信息s
  * @param username
  * @return 查找到的用户信息
  */
	User findByUsername(String username);
}