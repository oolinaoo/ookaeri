package web.fachist.mapper;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import web.fachist.entity.FachistVO;

public interface FachistMapper {
	
	Integer insertFacReserve(FachistVO fachistVO);
	List<FachistVO> listViewFacMem();
	List<FachistVO> listViewMemHist(@Param("memAcct") String memAcct);
	List<FachistVO> findHistTimeAmountByDate(@Param("facNo") Integer facNo, @Param("histDate") Date histDate);
	List<Map> listFacDateHistByMonth(@Param("facNo") Integer facNo, @Param("month") Integer month, @Param("year") Integer year);
	Integer deleteFachist(FachistVO fachistVO);
	Integer updateFachist(FachistVO fachistVO);
}
