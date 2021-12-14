package web.fac.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import web.fac.entity.FacVO;

public interface FacMapper {

	List<FacVO> listAll();
	FacVO photoByFacno(@Param("facNo") Integer facNo);
	
}
