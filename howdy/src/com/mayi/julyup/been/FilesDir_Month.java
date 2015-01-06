package com.mayi.julyup.been;

import java.util.ArrayList;

public class FilesDir_Month {
	String message;
	ArrayList<FilesDir_Month.Data> result = new ArrayList<FilesDir_Month.Data>();
	boolean success;
	int total;

	public class Data {
		String CATALOG_ID;
		String CATALOG_NAME;
		String FILE_COUNT;
		public String getCATALOG_ID() {
			return CATALOG_ID;
		}

		public void setCATALOG_ID(String cATALOG_ID) {
			CATALOG_ID = cATALOG_ID;
		}

		public String getCATALOG_NAME() {
			return CATALOG_NAME;
		}

		public void setCATALOG_NAME(String cATALOG_NAME) {
			CATALOG_NAME = cATALOG_NAME;
		}

		public String getFILE_COUNT() {
			return FILE_COUNT;
		}

		public void setFILE_COUNT(String fILE_COUNT) {
			FILE_COUNT = fILE_COUNT;
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
