package web.payment.mapper;

import java.sql.Timestamp;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import web.payment.entity.ManagementPaymentVO;

public interface ManagementPaymentMapper {
	List<ManagementPaymentVO> listAll();
	Integer add(ManagementPaymentVO managementPaymentVO);
	Integer addNotify(@Param("memAcct")String memAcct,@Param("addrNo") Integer addrNo,@Param("notifyMessage")String notifyMessage);
	Integer update(ManagementPaymentVO managementPaymentVO);
	Integer updatePayState(@Param("payDate")Timestamp payDate,@Param("memAcct")String memAcct);
	List<String> listMemAcct();
	List<Integer> listAddrNo();
	List<ManagementPaymentVO> unPaid(@Param("memAcct")String memAcct);
	List<ManagementPaymentVO> paid(@Param("memAcct")String memAcct);
}
