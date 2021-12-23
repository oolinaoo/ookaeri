package web.payment.entity;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ManagementPaymentVO implements Serializable {
	private Integer payNo;
	private String memAcct;
	private Integer addrNo;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Timestamp payDate;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date payDeadline;
	private Integer payAmount;
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Timestamp payRecentCall;
	private Integer payPeriod;
	private String payWay;
	private String adminAcct;
	private Integer payState;
	public ManagementPaymentVO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ManagementPaymentVO(Integer payNo, String memAcct, Integer addrNo, Timestamp payDate, Date payDeadline,
			Integer payAmount, Timestamp payRecentCall, Integer payPeriod, String payWay, String adminAcct,
			Integer payState) {
		super();
		this.payNo = payNo;
		this.memAcct = memAcct;
		this.addrNo = addrNo;
		this.payDate = payDate;
		this.payDeadline = payDeadline;
		this.payAmount = payAmount;
		this.payRecentCall = payRecentCall;
		this.payPeriod = payPeriod;
		this.payWay = payWay;
		this.adminAcct = adminAcct;
		this.payState = payState;
	}
	public Integer getPayNo() {
		return payNo;
	}
	public void setPayNo(Integer payNo) {
		this.payNo = payNo;
	}
	public String getMemAcct() {
		return memAcct;
	}
	public void setMemAcct(String memAcct) {
		this.memAcct = memAcct;
	}
	public Integer getAddrNo() {
		return addrNo;
	}
	public void setAddrNo(Integer addrNo) {
		this.addrNo = addrNo;
	}
	public Timestamp getPayDate() {
		return payDate;
	}
	public void setPayDate(Timestamp payDate) {
		this.payDate = payDate;
	}
	public Date getPayDeadline() {
		return payDeadline;
	}
	public void setPayDeadline(Date payDeadline) {
		this.payDeadline = payDeadline;
	}
	public Integer getPayAmount() {
		return payAmount;
	}
	public void setPayAmount(Integer payAmount) {
		this.payAmount = payAmount;
	}
	public Timestamp getPayRecentCall() {
		return payRecentCall;
	}
	public void setPayRecentCall(Timestamp payRecentCall) {
		this.payRecentCall = payRecentCall;
	}
	public Integer getPayPeriod() {
		return payPeriod;
	}
	public void setPayPeriod(Integer payPeriod) {
		this.payPeriod = payPeriod;
	}
	public String getPayWay() {
		return payWay;
	}
	public void setPayWay(String payWay) {
		this.payWay = payWay;
	}
	public String getAdminAcct() {
		return adminAcct;
	}
	public void setAdminAcct(String adminAcct) {
		this.adminAcct = adminAcct;
	}
	public Integer getPayState() {
		return payState;
	}
	public void setPayState(Integer payState) {
		this.payState = payState;
	}
	
	
	
	
	
	

}
