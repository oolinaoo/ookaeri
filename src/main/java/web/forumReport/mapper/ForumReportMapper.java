package web.forumReport.mapper;

import java.util.List;

import web.forumArticle.entity.ForumArticle;
import web.forumMessage.entity.ForumMessage;
import web.forumReport.entity.ForumReport;

public interface ForumReportMapper {
	
	List<ForumReport> listAll();
	List<ForumReport> artJoinAll();
	Integer add(ForumReport report);
	Integer update(ForumReport report);
	Integer updateState(ForumReport report);
}
