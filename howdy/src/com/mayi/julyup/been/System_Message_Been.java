/**
 * 2014-8-15下午11:44:36
 */
package com.mayi.julyup.been;


/**
 * @ClassName: LoginBeen
 * @package com.mayi.julyup.been
 * @author mayi
 * @date 2014-8-15 下午11:44:36
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @version 1.0
 * 
 */

public class System_Message_Been {
	String message;
	String  result ;
	boolean success;
	int total;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}

}
