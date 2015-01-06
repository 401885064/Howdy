/**
 * 2014-8-31上午10:08:39
 */
package com.mayi.julyup.been;

import java.util.ArrayList;

/**
 * @ClassName: Upload_Tasket_Img_Search_Show_Been
 * @package com.mayi.julyup.been
 * @author mayi
 * @date 2014-8-31 上午10:08:39
 * @Description: TODO(这里用一句话描述这个类的作用)
 * @version 1.0
 * 
 */
public class Upload_Tasket_Img_Search_Show_Been {
	String message;
	boolean success;
	String total;
	ArrayList<Data> result = new ArrayList<Upload_Tasket_Img_Search_Show_Been.Data>();

	public class Data {
		String IMAGE_ID;
		String IMAGE_NAME;
		String STATUS;
		String STATUS_VALUE;
		String SRC;
		String ROTATE;
		String RELATION_CODE;
		String CREATE_TIME;
		String LAST_MODIFY_TIME;
		
		public String getIMAGE_ID() {
			return IMAGE_ID;
		}
		public void setIMAGE_ID(String iMAGE_ID) {
			IMAGE_ID = iMAGE_ID;
		}
		public String getIMAGE_NAME() {
			return IMAGE_NAME;
		}
		public void setIMAGE_NAME(String iMAGE_NAME) {
			IMAGE_NAME = iMAGE_NAME;
		}
		public String getSTATUS() {
			return STATUS;
		}
		public void setSTATUS(String sTATUS) {
			STATUS = sTATUS;
		}
		public String getSRC() {
			return SRC;
		}
		public void setSRC(String sRC) {
			SRC = sRC;
		}
		public String getROTATE() {
			return ROTATE;
		}
		public void setROTATE(String rOTATE) {
			ROTATE = rOTATE;
		}
		public String getRELATION_CODE() {
			return RELATION_CODE;
		}
		public void setRELATION_CODE(String rELATION_CODE) {
			RELATION_CODE = rELATION_CODE;
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
		public String getSTATUS_VALUE() {
			return STATUS_VALUE;
		}
		public void setSTATUS_VALUE(String sTATUS_VALUE) {
			STATUS_VALUE = sTATUS_VALUE;
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
