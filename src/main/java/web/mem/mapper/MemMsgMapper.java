package web.mem.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import web.mem.entity.MemPackVO;
import web.payment.entity.ManagementPaymentVO;

public interface MemMsgMapper {
	List<MemPackVO> memJoinPack(@Param("memAcct") String memAcct);
	List<ManagementPaymentVO> memManagePay(@Param("memAcct") String memAcct);
}
