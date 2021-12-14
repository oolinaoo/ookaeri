package web.forumArticle.mapper;

import java.util.List;

import web.forumArticle.entity.ForumArticle;

public interface ForumArticleMapper {
	
	List<ForumArticle> listAll();
	Integer add(ForumArticle article);
	Integer update(ForumArticle article);
	List<ForumArticle> typeList(ForumArticle article);
	List<ForumArticle> artNoJoinMsg(ForumArticle article);
}
