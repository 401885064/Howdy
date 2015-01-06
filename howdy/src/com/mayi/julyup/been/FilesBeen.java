/**
 * 2014-9-29上午12:41:20
 */
package com.mayi.julyup.been;

import java.util.ArrayList;

/**
 * @ClassName: FilesBeen
 * @package com.mayi.julyup.been
 * @author mayi
 * @date 2014-9-29 上午12:41:20
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @version 1.0
 * 
 */
public class FilesBeen {
	String message;
	ArrayList<Data> result = new ArrayList<Data>();
	boolean success;
	int total;

	public class Data {
		String FILE_NAME;
		String CREATE_TIME;
		String LAST_MODIFY_TIME;
		String PATH;
		String YEAR;
		String MONTH;

		public String getFILE_NAME() {
			return FILE_NAME;
		}

		public void setFILE_NAME(String fILE_NAME) {
			FILE_NAME = fILE_NAME;
		}

		public String getCREATE_TIME() {
			return CREATE_TIME;
		}

		public void setCREATE_TIME(String cREATE_TIME) {
			CREATE_TIME = cREATE_TIME;
		}

		public String getLAST_MODIFY_TIME() {
			return LAST_MODIFY_TIME;
		}

		public void setLAST_MODIFY_TIME(String lAST_MODIFY_TIME) {
			LAST_MODIFY_TIME = lAST_MODIFY_TIME;
		}

		public String getPATH() {
			return PATH;
		}

		public void setPATH(String pATH) {
			PATH = pATH;
		}

		public String getYEAR() {
			return YEAR;
		}

		public void setYEAR(String yEAR) {
			YEAR = yEAR;
		}

		public String getMONTH() {
			return MONTH;
		}

		public void setMONTH(String mONTH) {
			MONTH = mONTH;
		}
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<Data> getResult() {
		return result;
	}

	public void setResult(ArrayList<Data> result) {
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
