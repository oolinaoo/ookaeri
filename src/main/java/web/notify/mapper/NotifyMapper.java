package web.notify.mapper;

import java.util.List;
import java.util.Map;

public interface NotifyMapper {
	List<String> listNotifyMsg();
	List<String> listNotifyTime();
	Map listNotify();
	Integer countNotifyState();
	Integer updateNotifyState();
	Integer deleteNotify();
}
