package com.wode.bangertong.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 返回结果
 */
@Getter
@Setter
public class Result {

	/**
	 * 正确返回结果
	 */
	public static final Integer RESULT_OK = 200;

	/**
	 * 接口响应正常，返回失败结果
	 */
	public static final Integer RESULT_FAIL = 201;

	public static final Integer VERIFY_CODE_TIME = 400;
	/**
	 * 服务器未知异常
	 */
	public static final Integer RESULT_ERROR = 500;


	public static final Integer STATUS_CLIENT_PARAMS_ERROR  = 602;
	/**
	 * 用户未登录
	 */
	public static final Integer RESULT_NOT_LOGIN = 603;

	/**
	 * 参数错误
	 */
	public static final Integer RESULT_PARAMETER_ERROR = 601;


	private Integer result;
	private Long timestamp;
	private Object data;
	private Object error;

	public Result() {
	}

	public Result(Integer result, Object data, Long timestamp) {
		super();
		this.result = result;
		if (result == 200) {
			this.data = data;
		} else {
			this.error = data;
		}
		this.timestamp = timestamp;
	}

	/**
	 * 用户未登录(专用)
	 */
	public Result(String str) {
		this.result = RESULT_NOT_LOGIN;
		this.error = "用户未登录";
		this.timestamp = new Date().getTime();
	}
}
