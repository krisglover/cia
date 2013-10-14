package com.itall.configure.server.models;


/**
 * Simple Response object following the JSend model http://labs.omniti.com/labs/jsend 
 * 
 * TODO : Should probably be turned into a generic model that can be reused
 * 
 * @author kglover
 *
 */
public class Response {

//	success	- All went well, and (usually) some data was returned.	Fill in (status = success, data)	
//	fail - There was a problem with the data submitted, or some pre-condition of the API call wasn't satisfied	Fill in (status = fail, data)	
//	error - An error occurred in processing the request, i.e. an exception was thrown Fill in (status = error, message	code, data )
	
	private String status;
	private Object data;
	private String message;
	private String code;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
