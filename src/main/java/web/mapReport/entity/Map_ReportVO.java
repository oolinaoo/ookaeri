package web.mapReport.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Map_ReportVO implements Serializable {
	private Integer MAP_REPT_NO;
	private String MAP_REPT_CONTENT;
	private Integer MAP_MSG_NO;
	private String MEM_ACCT;
	private Integer MAP_REPT_STATE;
	private String ADMIN_ACCT;
	public Map_ReportVO(Integer mAP_REPT_NO, String mAP_REPT_CONTENT, Integer mAP_MSG_NO, String mEM_ACCT,
			Integer mAP_REPT_STATE, String aDMIN_ACCT) {
		super();
		MAP_REPT_NO = mAP_REPT_NO;
		MAP_REPT_CONTENT = mAP_REPT_CONTENT;
		MAP_MSG_NO = mAP_MSG_NO;
		MEM_ACCT = mEM_ACCT;
		MAP_REPT_STATE = mAP_REPT_STATE;
		ADMIN_ACCT = aDMIN_ACCT;
	}
	
	
	
	
	public Map_ReportVO() {
		
	}




	public Integer getMAP_REPT_NO() {
		return MAP_REPT_NO;
	}
	public void setMAP_REPT_NO(Integer mAP_REPT_NO) {
		MAP_REPT_NO = mAP_REPT_NO;
	}
	public String getMAP_REPT_CONTENT() {
		return MAP_REPT_CONTENT;
	}
	public void setMAP_REPT_CONTENT(String mAP_REPT_CONTENT) {
		MAP_REPT_CONTENT = mAP_REPT_CONTENT;
	}
	public Integer getMAP_MSG_NO() {
		return MAP_MSG_NO;
	}
	public void setMAP_MSG_NO(Integer mAP_MSG_NO) {
		MAP_MSG_NO = mAP_MSG_NO;
	}
	public String getMEM_ACCT() {
		return MEM_ACCT;
	}
	public void setMEM_ACCT(String mEM_ACCT) {
		MEM_ACCT = mEM_ACCT;
	}
	public Integer getMAP_REPT_STATE() {
		return MAP_REPT_STATE;
	}
	public void setMAP_REPT_STATE(Integer mAP_REPT_STATE) {
		MAP_REPT_STATE = mAP_REPT_STATE;
	}
	public String getADMIN_ACCT() {
		return ADMIN_ACCT;
	}
	public void setADMIN_ACCT(String aDMIN_ACCT) {
		ADMIN_ACCT = aDMIN_ACCT;
	}
	
	

	

}



