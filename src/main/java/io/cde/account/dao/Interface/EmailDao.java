package io.cde.account.dao.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.cde.account.beans.Email;
/**
 * @author lcl
 * @createDate 2016年11月3日下午3:19:02
 * 使用jpa操作邮箱数据接口
 */
@Repository
public interface EmailDao extends JpaRepository<Email, String> {
  /**
  * 根据邮箱id获取邮箱信息
  * @param id
  * @return
  */
  Email findById(String id);
  /**
  * 根据邮箱名称获取邮箱信息
  * @param email
  * @return
  */
  Email findByEmail(String email);
  /**
  * 根据用户是否有默认邮箱
  * @param userId
  * @param isDefault
  * @return
  */
  Email findByUserIdAndEmail(String userId, String email);
}
