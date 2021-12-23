package web.notify.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface NotifyMapper {
	List<String> listNotifyMsg(@Param("memAcct")String memAcct);
	List<String> listNotifyTime(@Param("memAcct")String memAcct);
	Integer countNotifyState(@Param("memAcct")String memAcct);
	Integer updateNotifyState(@Param("memAcct")String memAcct);
	Integer deleteNotify(@Param("memAcct")String memAcct);
}
