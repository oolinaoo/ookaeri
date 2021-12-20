package web.pack.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import web.pack.entity.PackageVO;

public interface PackMapper {

	List<PackageVO> listAll();
	List<Integer> listAddr();
	Integer add(PackageVO packageVO);
	Integer update(PackageVO packageVO);
	Integer addNotify(@Param("memAcct")String memAcct,@Param("addrNo") Integer addrNo);
	String memAcct(Integer addrNo);
	List<String> listNotifyMsg();
	List<String> listNotifyTime();
	Map listNotify();
	Integer countNotifyState();
	Integer updateNotifyState();
	Integer deleteNotify();
	
}
