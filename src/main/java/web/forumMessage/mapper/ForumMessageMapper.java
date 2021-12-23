package web.forumMessage.mapper;

import java.util.List;

import web.forumMessage.entity.ForumMessage;
import web.forumMessage.entity.ForumMessageBo;

public interface ForumMessageMapper {
	
	List<ForumMessage> listAll();
	List<ForumMessageBo> msgJoinArtJoinType();
	List<ForumMessageBo> msgJoinRept();
	Integer add(ForumMessage message);
	Integer update(ForumMessage message);

}
