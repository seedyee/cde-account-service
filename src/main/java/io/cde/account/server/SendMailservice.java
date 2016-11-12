package io.cde.account.server;

import java.util.Properties;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * @author lcl
 * @createDate 2016年11月3日下午11:49:20
 * 发送邮件的服务
 */
@Service
@Transactional
public class SendMailservice {
	
	@Autowired
  JavaMailSender mailSender;

  public Map<String, Object> sendMailTest(String toUserName) {
    Map<String, Object> result = new HashMap<>();
    if (toUserName != null) {
      try {
        System.out.println("进入书写邮件界面.....");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("lichanglin.lcl@gmail.com");
        message.setTo("lichanglin.lcl@gmail.com");
        message.setSubject("注册测试邮件");
        message.setText("测试邮件!!!!");
        Properties properties = System.getProperties();
        System.out.println("服务器认证方式：" + properties.getProperty("spring.mail.host"));
        System.out.println("服务器认证方式：" + properties.getProperty("spring.mail.properties.mail.smtp.auth"));
        mailSender.send(message);
        result.put("error", "null");
        return result;
      }catch (Exception e) {
        e.printStackTrace();
        result.put("error", "网络链接异常！！！");
        return result;
      }
    }
    result.put("error", "发送失败");
    return result;
  }
}
