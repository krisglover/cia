package com.itall.configure.server.models;


/**
 * POJO used to model a service error
 *
 *JSON Sample -
 *"error": {
 *  "code": "serviceCode23", --service level error code. Codes should be partitioned by service so there's no overlap
 *  "msg": "service specific acception"
 * @author kglover
 *
 */

public class Error {
	
	private String code;
	private String msg;
	
	protected Error() {}
	
	public Error(String code,String msg )
	{
		this.code = code;
		this.msg = msg;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getMsg() {
		return msg;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append(getClass().getSimpleName() + " [code=").append(code).append(", msg=").append(msg).append("]");
		return builder.toString();
	}
}
