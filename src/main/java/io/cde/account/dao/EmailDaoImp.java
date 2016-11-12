package io.cde.account.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import io.cde.account.beans.Email;
/**
 * @author lcl
 * @createDate 2016年11月7日下午2:16:58
 * 邮箱数据操作
 */
@Repository
public class EmailDaoImp {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
  * 判断邮箱是否被注册过或是关联过
  * @param email
  * @return 查找到的邮箱信息
  */
  public int checkByEmail(String email){
    int emailResult;
    String sql = "select count(f_email) from f_email where f_email = ?";
    emailResult = jdbcTemplate.queryForObject(sql, new Object[]{email}, Integer.class);
    return emailResult;
  }
  /**
  * 判断邮箱是否是默认邮箱并返回
  * @param email
  * @return
  */
  public Email getByDefalutEmail(String email){
    Email emailResult;
    int check = this.checkByEmail(email);
    if (check == 0) {
      return null;
    }
    String sql = "select f_id id, f_email email, f_user_id userId, f_default isDefault,"
      + " f_verified isVerified, f_publice isPublice "
      + "from f_email where f_email = ? and f_default = 1";
    RowMapper<Email> rowMapper = new BeanPropertyRowMapper<>(Email.class);
    emailResult = jdbcTemplate.queryForObject(sql, rowMapper, new Object[]{email});
    return emailResult;
  }
  /**
  * 根据用户id获取用户邮箱
  * @param userId
  * @return 用户邮箱的list集合
  */
  public List<Map<String, Object>> getEmailsByUserId(String userId) {
    List<Map<String, Object>> emails = null;
    String sql = "select f_id id, f_user_id userId, f_email email, f_verified isVerified from f_email where f_user_id = ?";
    try {
      emails = jdbcTemplate.queryForList(sql, userId);
    }catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return emails;
  }
  /**
  * 批量删除邮箱，其中不包括默认邮箱
  * @param emails
  * @return
  */
  public int[] deleteEmails (List<String> emails) {
    String sql = "delete from f_email where f_id = ?";
    int[] result = null;
    try {
      result = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
          for(int j = 0; j < emails.size(); j++) {
            ps.setString(j + 1, emails.get(j));
          }
        }
        @Override
        public int getBatchSize() {
            return emails.size();
          }
        });
    }catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
    return result;
  }
}
