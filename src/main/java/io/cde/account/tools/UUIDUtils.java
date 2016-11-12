package io.cde.account.tools;

import java.io.Serializable;
import java.util.Properties;

import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.id.UUIDHexGenerator;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.type.Type;
/**
 * @author lcl
 * @createDate 2016年11月4日上午10:14:35
 * 生成唯一Id的工具类
 */
public class UUIDUtils extends UUIDHexGenerator{

  private String entityName;

  @Override
  public void configure(Type type, Properties params, ServiceRegistry serviceRegistry) throws MappingException {
    entityName = params.getProperty(ENTITY_NAME);
    super.configure(type, params, serviceRegistry);
  }

  @Override
  public Serializable generate(SessionImplementor session, Object object) throws HibernateException {
    Serializable id = session.getEntityPersister(entityName, object).getIdentifier(object, session);       
    if (id == null) {
      return super.generate(session, object);
    }
    Serializable id1 = "45646545";
    return id1;
  }
}
