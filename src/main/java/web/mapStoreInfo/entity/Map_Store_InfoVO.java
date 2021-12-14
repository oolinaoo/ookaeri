package web.mapStoreInfo.entity;


	
	import java.io.Serializable;
	import java.sql.Date;
	import java.sql.Timestamp;

	public class Map_Store_InfoVO implements Serializable {
		
		private String MAP_STORE_NO;
		private String MAP_STORE_NAME;
		private String MAP_STORE_ADDR;
		
		
		
		public Map_Store_InfoVO() {
			
			
		}
		public Map_Store_InfoVO(String mAP_STORE_NO, String mAP_STORE_NAME, String mAP_STORE_ADDR) {
			super();
			MAP_STORE_NO = mAP_STORE_NO;
			MAP_STORE_NAME = mAP_STORE_NAME;
			MAP_STORE_ADDR = mAP_STORE_ADDR;
		}
		public String getMAP_STORE_NO() {
			return MAP_STORE_NO;
		}
		public void setMAP_STORE_NO(String mAP_STORE_NO) {
			MAP_STORE_NO = mAP_STORE_NO;
		}
		public String getMAP_STORE_NAME() {
			return MAP_STORE_NAME;
		}
		public void setMAP_STORE_NAME(String mAP_STORE_NAME) {
			MAP_STORE_NAME = mAP_STORE_NAME;
		}
		public String getMAP_STORE_ADDR() {
			return MAP_STORE_ADDR;
		}
		public void setMAP_STORE_ADDR(String mAP_STORE_ADDR) {
			MAP_STORE_ADDR = mAP_STORE_ADDR;
		}
		
		
		
		

	}



