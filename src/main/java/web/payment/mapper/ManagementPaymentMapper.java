package web.payment.mapper;

import java.util.List;

import web.payment.entity.ManagementPaymentVO;

public interface ManagementPaymentMapper {
	List<ManagementPaymentVO> listAll();
}
