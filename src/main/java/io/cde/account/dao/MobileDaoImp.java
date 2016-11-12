package io.cde.account.dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
/**
 * @author lcl
 * @createDate 2016年11月7日下午5:40:39
 *
 */
@Repository
public class MobileDaoImp {

  @Autowired
  private JdbcTemplate jdbcTemplate;

  /**
  * 根据用户id获取用户的所有电话号码list
  * @param userId
  * @return 返回用户电话号码list
  */
  public List<Map<String, Object>> getMobilesByUserId(String userId) {
    List<Map<String, Object>> mobiles = null;
    String sql = "select f_id id, f_user_id userId, f_mobile mobile, f_verified isVerified from f_mobile where f_user_id = ?";
    try {
      mobiles = jdbcTemplate.queryForList(sql, new Object[]{userId});
    }catch (Exception e) {
      e.printStackTrace();
      return null;
    }
    return mobiles;
  }
  /**
  * 批量删除电话，默认的电话号码也可以删除
  * @param mobiles
  * @return
  */
  public int[] deleteMobiles (List<String> mobiles) {
    String sql = "delete from f_mobile where f_id = ?";
    int[] result = null;
    try {
      result = jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
        @Override
        public void setValues(PreparedStatement ps, int i) throws SQLException {
          for(int j = 0; j < mobiles.size(); j++) {
            ps.setString(j + 1, mobiles.get(j));
          }
        }
        @Override
        public int getBatchSize() {
          return mobiles.size();
        }
      });
    }catch (Exception e) {
      e.printStackTrace();
      return null;
    } 
    return result;
  }
}
