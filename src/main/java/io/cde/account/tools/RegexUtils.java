package io.cde.account.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author lcl
 * 用来检测用户名、密码、邮箱是否符合要求的工具类
 */
public class RegexUtils {
    
	/**
	 * 匹配用户名正则表达式字符串.
	 * 
	 * 规则：
	 */
	private static final String ACCOUNT_NAME_REGEX = "^[a-zA-Z][a-zA-Z0-9]{2,15}$";
	/**
	 * 匹配用户密码正则表示式字符串.
	 * 
	 * 规则：
	 */
	private static final String ACCOUNT_PASSWORD_REGEX = "((?=.*\\d)(?=.*\\D)|(?=.*[a-zA-Z])(?=.*[^a-zA-Z]))^.{6,24}$";
	/**
	 * 匹配用户邮箱正则表示式字符串.
	 * 
	 * 规则：
	 */
	private static final String ACCOUNT_EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";
	/**
	 * 匹配用户电话号码正则表示式字符串.
	 * 
	 * 规则：1开头，总由11位数字组成
	 */
	private static final String ACCOUNT_MOBILE_REGEX = "^[1][3-8]+\\d{9}";
    
	private static Pattern pattern = null;
	
	private static Matcher matcher = null;
	/**
	 * 匹配用户名.
	 * 
	 * @param name 要匹配的用户名
	 * @return 用户名符合正则表达式规则返回true，否则返回false
	 */
	public static boolean isAccountName(String name) {
		pattern = Pattern.compile(ACCOUNT_NAME_REGEX);
		matcher = pattern.matcher(name);
		return matcher.find();
	}
	
	/**
	 * 匹配用户密码.
	 * 
	 * @param password 要匹配的用户密码
	 * @return 用户名符合正则表达式规则返回true，否则返回false
	 */
    public static boolean isAccountPassword(String password) {
    	    pattern = Pattern.compile(ACCOUNT_PASSWORD_REGEX);
		matcher = pattern.matcher(password);
		return matcher.find();
	}
    
    /**
     * 匹配邮箱.
     * 
     * @param email 要匹配的邮箱地址
     * @return 用户名符合正则表达式规则返回true，否则返回false
     */
    public static boolean isEmail(String email) {
      	pattern = Pattern.compile(ACCOUNT_EMAIL_REGEX, Pattern.CASE_INSENSITIVE);
		matcher = pattern.matcher(email);
		return matcher.find();
    }
    
    /**
     * 匹配电话号码.
     * 
     * @param mobile 要匹配的电话号码
     * @return 用户名符合正则表达式规则返回true，否则返回false
     */
    public static boolean isMobile(String mobile) {
    	    pattern = Pattern.compile(ACCOUNT_MOBILE_REGEX);
		matcher = pattern.matcher(mobile);
		return matcher.find();
    }
}
