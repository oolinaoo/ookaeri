package web.mapMessage.entity;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Map_MessageVO implements Serializable {
	private Integer MAP_MSG_NO;
	private String MAP_STORE_NO;
	private String MEM_ACCT;
	private String MAP_MSG_CONTENT;
	private Timestamp MAP_MSG_TIME;
	private Integer MAP_MSG_STATE;
	
	
	public Map_MessageVO() {
	}
	
	public Map_MessageVO(Integer MAP_MSG_NO, String MAP_STORE_NO, String MEM_ACCT, String MAP_MSG_CONTENT, Timestamp MAP_MSG_TIME, Integer MAP_MSG_STATE) {
		this.MAP_MSG_NO = MAP_MSG_NO;
		this.MAP_STORE_NO = MAP_STORE_NO;
		this.MEM_ACCT = MEM_ACCT;
		this.MAP_MSG_CONTENT = MAP_MSG_CONTENT;
		this.MAP_MSG_TIME = MAP_MSG_TIME;
		this.MAP_MSG_STATE = MAP_MSG_STATE;
		
	}

	public Integer getMAP_MSG_NO() {
		return MAP_MSG_NO;
	}

	public void setMAP_MSG_NO(Integer mAP_MSG_NO) {
		MAP_MSG_NO = mAP_MSG_NO;
	}

	public String getMAP_STORE_NO() {
		return MAP_STORE_NO;
	}

	public void setMAP_STORE_NO(String mAP_STORE_NO) {
		MAP_STORE_NO = mAP_STORE_NO;
	}

	public String getMEM_ACCT() {
		return MEM_ACCT;
	}

	public void setMEM_ACCT(String mEM_ACCT) {
		MEM_ACCT = mEM_ACCT;
	}

	public String getMAP_MSG_CONTENT() {
		return MAP_MSG_CONTENT;
	}

	public void setMAP_MSG_CONTENT(String mAP_MSG_CONTENT) {
		MAP_MSG_CONTENT = mAP_MSG_CONTENT;
	}

	public Timestamp getMAP_MSG_TIME() {
		return MAP_MSG_TIME;
	}

	public void setMAP_MSG_TIME(Timestamp mAP_MSG_TIME) {
		MAP_MSG_TIME = mAP_MSG_TIME;
	}

	public Integer getMAP_MSG_STATE() {
		return MAP_MSG_STATE;
	}

	public void setMAP_MSG_STATE(Integer mAP_MSG_STATE) {
		MAP_MSG_STATE = mAP_MSG_STATE;
	}
	
	

}




