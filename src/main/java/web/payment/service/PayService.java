
package web.payment.service;


import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import web.payment.dao.Management_PaymentDAO;
import web.payment.dao.impl.Management_PaymentDAOImpl;
import web.payment.entity.ManagementPaymentVO;



public class PayService {
	private Management_PaymentDAO dao;
	
	public PayService() {
		dao = new Management_PaymentDAOImpl();
	}
	public ManagementPaymentVO addPay( String memAcct, Integer addrNo, Date payDeadline,
			Integer payAmount, Integer payPeriod, String adminAcct,
			Integer payState) {
		ManagementPaymentVO payvo = new ManagementPaymentVO();
		
		payvo.setMemAcct(memAcct);
		payvo.setAddrNo(addrNo);
//		payvo.setPay_date(pay_date);
		payvo.setPayDeadline(payDeadline);
		payvo.setPayAmount(payAmount);
//		payvo.setPay_recent_call(pay_recent_call);
		payvo.setPayPeriod(payPeriod);
//		payvo.setPay_way(pay_way);
		payvo.setAdminAcct(adminAcct);
		payvo.setPayState(payState);
		
		dao.add(payvo);
		
		return payvo;
	}
	
	public ManagementPaymentVO updatePay(Integer payNo, String memAcct, Integer addrNo, Date payDeadline,
			Integer payAmount, Integer payPeriod, String payWay, String adminAcct,
			Integer payState) {
		ManagementPaymentVO payvo = new ManagementPaymentVO();
		
		payvo.setPayNo(payNo);
		payvo.setMemAcct(memAcct);
		payvo.setAddrNo(addrNo);
//		payvo.setPay_date(pay_date);
		payvo.setPayDeadline(payDeadline);
		payvo.setPayAmount(payAmount);
//		payvo.setPay_recent_call(pay_recent_call);
		payvo.setPayPeriod(payPeriod);
		payvo.setPayWay(payWay);
		payvo.setAdminAcct(adminAcct);
		payvo.setPayState(payState);
		dao.update(payvo);
		
		return payvo;
	}

	public void deletePay(Integer payNo) {
		dao.delete(payNo);
	}

	public ManagementPaymentVO getOnePay(Integer payNo) {
		return dao.findByPK(payNo);
	}

	public List<ManagementPaymentVO> getAll() {
		return dao.getAll();
	}

}
