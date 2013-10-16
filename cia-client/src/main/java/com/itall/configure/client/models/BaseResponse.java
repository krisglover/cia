package com.itall.configure.client.models;


/**
 * Simple Response object following the JSend model http://labs.omniti.com/labs/jsend 
 * 
 * Any class extending this one must provide the expected data type <T> as the generic for the json provider to properly deserialize 
 * 
 * @author kglover
 *
 */
public abstract class BaseResponse<T> {

//	success	- All went well, and (usually) some data was returned.	Fill in (status = success, data)	
//	fail - There was a problem with the data submitted, or some pre-condition of the API call wasn't satisfied	Fill in (status = fail, data)	
//	error - An error occurred in processing the request, i.e. an exception was thrown Fill in (status = error, message	code, data )
	
	private String status;
	private T data;
	private String message;
	private String code;
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
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
