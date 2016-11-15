package io.cde.account.tools;

import java.util.HashMap;
import java.util.Map;

import io.cde.account.domaim.resultmodel.Error;
import io.cde.account.domaim.resultmodel.Model;

/**
 * @author lcl
 * @createDate 2016年11月14日上午11:14:31
 * 用来组装返回结果的工具类
 */
public class ResultUtils {
	private static Model model;
	private static Error error;
	
	/**
	 * 返回数据和null错误对象
	 * @param object
	 * @return
	 */
	public static Object result(Object object) {
		model = new Model();
		model.setResult(object);
		model.setError(null);
		return model;
	}
	/**
	 * 返回错误对象
	 * @param code
	 * @param message
	 * @return
	 */
	public static Object resultError(int code, String message) {
		Map<String, Error> resultError = new HashMap<>();
		error = new Error();
		error.setCode(code);
		error.setMessage(message);
		resultError.put("error", error);
		return resultError;
	}
	/**
	 * 操作成功返回一个null错误对象
	 * @return
	 */
	public static Object resultNullError(){
		Map<String, Error> resultError = new HashMap<>();
		resultError.put("error", null);
		return resultError;
	}
	
}
