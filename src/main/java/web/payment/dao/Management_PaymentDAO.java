package web.payment.dao;

import java.util.List;

import web.payment.entity.ManagementPaymentVO;

public interface Management_PaymentDAO {
	// 此介面定義對資料庫的相關存取抽象方法
		void add(ManagementPaymentVO management_payment);
		void update(ManagementPaymentVO management_payment);
		void delete(int payNo);
		ManagementPaymentVO findByPK(int payNo);
		List<ManagementPaymentVO> getAll();

}
