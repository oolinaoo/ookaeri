package web.v_map_message_map_report.entity;


import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class mapMessageMapReportVO {
	private Integer mapMassageNo;
	private String mapStoreNo;
	private String memAcct;
	private String mapMessageContent;
	private Timestamp mapMessageTime;
	private Integer mapMessageState;
	private Integer mapReptState;
	private String mapReptContent;
	
	public mapMessageMapReportVO()
	{
		
	}
	
	public mapMessageMapReportVO(Integer mapMassageNo, String mapStoreNo, String memAcct, String mapMessageContent,
			Timestamp mapMessageTime, Integer mapMessageState, Integer mapReptState, String mapReptContent) {
		super();
		this.mapMassageNo = mapMassageNo;
		this.mapStoreNo = mapStoreNo;
		this.memAcct = memAcct;
		this.mapMessageContent = mapMessageContent;
		this.mapMessageTime = mapMessageTime;
		this.mapMessageState = mapMessageState;
		this.mapReptState = mapReptState;
		this.mapReptContent = mapReptContent;
	}

	public Integer getMapMassageNo() {
		return mapMassageNo;
	}

	public void setMapMassageNo(Integer mapMassageNo) {
		this.mapMassageNo = mapMassageNo;
	}

	public String getMapStoreNo() {
		return mapStoreNo;
	}

	public void setMapStoreNo(String mapStoreNo) {
		this.mapStoreNo = mapStoreNo;
	}

	public String getMemAcct() {
		return memAcct;
	}

	public void setMemAcct(String memAcct) {
		this.memAcct = memAcct;
	}

	public String getMapMessageContent() {
		return mapMessageContent;
	}

	public void setMapMessageContent(String mapMessageContent) {
		this.mapMessageContent = mapMessageContent;
	}

	public Timestamp getMapMessageTime() {
		return mapMessageTime;
	}

	public void setMapMessageTime(Timestamp mapMessageTime) {
		this.mapMessageTime = mapMessageTime;
	}

	public Integer getMapMessageState() {
		return mapMessageState;
	}

	public void setMapMessageState(Integer mapMessageState) {
		this.mapMessageState = mapMessageState;
	}

	public Integer getMapReptState() {
		return mapReptState;
	}

	public void setMapReptState(Integer mapReptState) {
		this.mapReptState = mapReptState;
	}

	public String getMapReptContent() {
		return mapReptContent;
	}

	public void setMapReptContent(String mapReptContent) {
		this.mapReptContent = mapReptContent;
	}
	
	
	

}
