package io.cde.account.dao.Interface;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import io.cde.account.beans.Mobile;
/**
 * @author lcl
 * @createDate 2016年11月3日下午3:28:53
 * 使用jpa操作电话数据接口
 */
@Repository
public interface MobileDao extends JpaRepository<Mobile, String> {
  /**
  * 根据电话号码id获取电话号码信息
  * @param id
  * @return
  */
	Mobile findById(String id);
  /**
  * 查询用户的默认电话
  * @param userId
  * @param isDefault
  * @return
  */
	Mobile findByUserIdAndMobile(String userId, String mobile);
  /**
  * 根据电话号码获取电话信息
  * @param mobile
  * @return
  */
	Mobile findByMobile(String mobile);
}