package web.mapFavorite.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Map_My_FavoriteVO implements Serializable {
	
	private String MAP_STORE_NO;
	private String MEM_ACCT;
	
	
	
	
	public Map_My_FavoriteVO() {
		super();
	}
	public Map_My_FavoriteVO(String mAP_STORE_NO, String mEM_ACCT) {
		super();
		MAP_STORE_NO = mAP_STORE_NO;
		MEM_ACCT = mEM_ACCT;
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
	
	
	

	


}
