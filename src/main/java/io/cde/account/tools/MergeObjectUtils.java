package io.cde.account.tools;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * @author lcl
 * @createDate 2016年11月3日上午11:42:11
 * 用来合并同类对象的属性的工具类
 */
public class MergeObjectUtils {
  /**
  * 合并对象属性
  * @param formerObject 原来的对象
  * @param updateObject 需要把属性合并到原来对象的对象
  * @return 合并以后的对象
  */
  public static Object merge(Object formerObject, Object updateObject) {
    try {
      Field[] o1Fields = formerObject.getClass().getDeclaredFields();
      for (Field o1Field : o1Fields) {
        if (!o1Field.getName().equals("serialVersionUID")) {
          String fName = o1Field.getName();
          Method m = formerObject.getClass().getMethod("get" + toUpperCaseFirstOne(fName));
          Object value1 = m.invoke(formerObject);
          Object value2 = m.invoke(updateObject);
          if (value2 != null && value1 != value2) {
            if (value2 instanceof String) {
	            m = formerObject.getClass().getMethod("set" + toUpperCaseFirstOne(fName), String.class);
	            m.invoke(formerObject, value2);
            }
            if (value2 instanceof Integer) {
	            m = formerObject.getClass().getMethod("set" + toUpperCaseFirstOne(fName), Integer.class);
	            m.invoke(formerObject, value2);
            }
            if (value2 instanceof Long) {
	            m = formerObject.getClass().getMethod("set" + toUpperCaseFirstOne(fName), Long.class);
	            m.invoke(formerObject, value2);
            }
            if (value2 instanceof List) {
	            m = formerObject.getClass().getMethod("set" + toUpperCaseFirstOne(fName), List.class);
	            m.invoke(formerObject, value2);
            }
            if (value2 instanceof Map) {
	            m = formerObject.getClass().getMethod("set" + toUpperCaseFirstOne(fName), Map.class);
	            m.invoke(formerObject, value2);
            }
            if (value2 instanceof Boolean) {
            	    m = formerObject.getClass().getMethod("set" + toUpperCaseFirstOne(fName), Boolean.class);
            	    m.invoke(formerObject, value2);
            }
          }
        }
      }
    }catch (Exception e) {
      e.printStackTrace();
    }
    return formerObject;
  }
  /**
   * 把字符串的第一个字符转换为大写
   * @param fName
   * @return
   */
  private static String toUpperCaseFirstOne(String fName) {
  	return fName.replaceFirst(fName.charAt(0) + "", (fName.charAt(0) + "").toUpperCase());
  }
}
