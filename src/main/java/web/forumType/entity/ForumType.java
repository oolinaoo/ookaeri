package web.forumType.entity;

import java.io.Serializable;

public class ForumType implements Serializable{
	
	private Integer forTypeNo;
	private String forType;
	
	public ForumType() {
	}

	public ForumType(Integer forTypeNo, String forType) {
		super();
		this.forTypeNo = forTypeNo;
		this.forType = forType;
	}

	public Integer getForTypeNo() {
		return forTypeNo;
	}

	public void setForTypeNo(Integer forTypeNo) {
		this.forTypeNo = forTypeNo;
	}

	public String getForType() {
		return forType;
	}

	public void setForType(String forType) {
		this.forType = forType;
	}
	
}
