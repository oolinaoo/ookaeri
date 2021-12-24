package web.fachist.mapper;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import web.fachist.entity.FachistVO;

public interface FachistMapper {
	
	int insertFacReserve(FachistVO fachistVO);
	
	List<FachistVO> listViewFacMem();
	List<FachistVO> findHistTimeAmountByDate(@Param("facNo") Integer facNo, @Param("histDate") Date histDate);
	List<Map> listFacDateHistByMonth(@Param("facNo") Integer facNo, @Param("month") Integer month);
	Integer deleteFachist(FachistVO fachistVO);
	List<FachistVO> listViewMemHist(@Param("memAcct") String memAcct);
	
}
