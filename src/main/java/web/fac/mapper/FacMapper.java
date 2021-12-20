package web.fac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import web.fac.entity.FacVO;

public interface FacMapper {

	List<FacVO> listAll();
	String[] listAllDate(@Param("facNo") Integer facNo);
	Integer listStartTime(@Param("facNo") Integer facNo);
	Integer listEndTime(@Param("facNo") Integer facNo);
	FacVO photoByFacno(@Param("facNo") Integer facNo);
	List<FacVO> listByAddr(@Param("facAddr") String facAddr);
	
	Integer insertNewFac(FacVO facVO);
	Integer findNewestFacNo();
	Integer insertNewFacDate(@Param("facNo") Integer facNo, @Param("facOpenDate") String facOpenDate);
	Integer insertNewFacTime(@Param("facNo") Integer facNo, @Param("facOpenTime") String facOpenTime, 
			@Param("startTime") Integer startTime, @Param("endTime") Integer endTime);
	
	List<FacVO> facDetail(@Param("facNo") Integer facNo);
}
