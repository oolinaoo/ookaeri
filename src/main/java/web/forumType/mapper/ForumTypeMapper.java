package web.forumType.mapper;

import java.util.List;

import web.forumType.entity.ForumType;

public interface ForumTypeMapper {
	
	List<ForumType> listAll();
	Integer add(ForumType type);
	Integer update(ForumType type);

}
