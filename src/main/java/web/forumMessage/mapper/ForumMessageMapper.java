package web.forumMessage.mapper;

import java.util.List;

import web.forumMessage.entity.ForumMessage;

public interface ForumMessageMapper {
	
	List<ForumMessage> listAll();
	Integer add(ForumMessage message);
	Integer update(ForumMessage message);

}
