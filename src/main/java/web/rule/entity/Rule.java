package web.rule.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="RULE")
public class Rule implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="RULE_NO")
	private Integer rule_no;
	
	@Column(name="RULE_TITLE")
	private String rule_title;
	
	@Column(name="RULE_CONTENT")
	private String rule_content;
	
	@Column(name="RULE_POSTTIME")
	private Date rule_posttime;
	
	@Column(name="ADMIN_ACCT")
	private String admin_acct;
	
	public Integer getRule_no() {
		return rule_no;
	}
	public void setRule_no(Integer rule_no) {
		this.rule_no = rule_no;
	}
	public String getRule_title() {
		return rule_title;
	}
	public void setRule_title(String rule_title) {
		this.rule_title = rule_title;
	}
	public String getRule_content() {
		return rule_content;
	}
	public void setRule_content(String rule_content) {
		this.rule_content = rule_content;
	}
	public Date getRule_posttime() {
		return rule_posttime;
	}
	public void setRule_posttime(Date rule_posttime) {
		this.rule_posttime = rule_posttime;
	}
	public String getAdmin_acct() {
		return admin_acct;
	}
	public void setAdmin_acct(String admin_acct) {
		this.admin_acct = admin_acct;
	}

}
