/**
 * 2014-8-31上午10:08:39
 */
package com.mayi.julyup.been;

import java.util.ArrayList;

/**
 * @ClassName: Upload_unfinish_Been
 * @package com.mayi.julyup.been
 * @author mayi
 * @date 2014-8-31 上午10:08:39
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @version 1.0
 * 
 */

public class Upload_finish_Been {
	String message;
	boolean success;
	String total;
	ArrayList<Data> result = new ArrayList<Upload_finish_Been.Data>();

	public class Data {
		String TASK_ID;
		String TASK_NAME;
		String STATUS;
		String STATUS_VALUE;
		String CREATE_TIME;
		String LAST_MODIFY_TIME;
		String REMARK;
		String CATALOG_ID;
		String USER_ID;

		public String getTASK_ID() {
			return TASK_ID;
		}

		public void setTASK_ID(String tASK_ID) {
			TASK_ID = tASK_ID;
		}

		public String getTASK_NAME() {
			return TASK_NAME;
		}

		public void setTASK_NAME(String tASK_NAME) {
			TASK_NAME = tASK_NAME;
		}

		public String getSTATUS() {
			return STATUS;
		}

		public void setSTATUS(String sTATUS) {
			STATUS = sTATUS;
		}

		public String getSTATUS_VALUE() {
			return STATUS_VALUE;
		}

		public void setSTATUS_VALUE(String sTATUS_VALUE) {
			STATUS_VALUE = sTATUS_VALUE;
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

		public String getREMARK() {
			return REMARK;
		}

		public void setREMARK(String rEMARK) {
			REMARK = rEMARK;
		}

		public String getCATALOG_ID() {
			return CATALOG_ID;
		}

		public void setCATALOG_ID(String cATALOG_ID) {
			CATALOG_ID = cATALOG_ID;
		}

		public String getUSER_ID() {
			return USER_ID;
		}

		public void setUSER_ID(String uSER_ID) {
			USER_ID = uSER_ID;
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

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
