package web.rule.entity;

import java.io.Serializable;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class Rule implements Serializable{
	
	private Integer ruleNo;
	private String ruleTitle;
	private String ruleContent;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date rulePosttime;
	private String adminAcct;
	
	public Rule() {

	}

	public Rule(Integer ruleNo, String ruleTitle, String ruleContent, Date rulePosttime, String adminAcct) {
		super();
		this.ruleNo = ruleNo;
		this.ruleTitle = ruleTitle;
		this.ruleContent = ruleContent;
		this.rulePosttime = rulePosttime;
		this.adminAcct = adminAcct;
	}

	public Integer getRuleNo() {
		return ruleNo;
	}

	public void setRuleNo(Integer ruleNo) {
		this.ruleNo = ruleNo;
	}

	public String getRuleTitle() {
		return ruleTitle;
	}

	public void setRuleTitle(String ruleTitle) {
		this.ruleTitle = ruleTitle;
	}

	public String getRuleContent() {
		return ruleContent;
	}

	public void setRuleContent(String ruleContent) {
		this.ruleContent = ruleContent;
	}

	public Date getRulePosttime() {
		return rulePosttime;
	}

	public void setRulePosttime(Date rulePosttime) {
		this.rulePosttime = rulePosttime;
	}

	public String getAdminAcct() {
		return adminAcct;
	}

	public void setAdminAcct(String adminAcct) {
		this.adminAcct = adminAcct;
	}
	
}
